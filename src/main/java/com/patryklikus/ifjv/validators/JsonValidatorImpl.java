/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.IntegerSchema;
import com.patryklikus.ifjv.schemas.ObjectSchema;
import com.patryklikus.ifjv.schemas.Schema;
import gnu.trove.list.array.TCharArrayList;
import gnu.trove.list.linked.TCharLinkedList;

import java.util.HashSet;
import java.util.Set;

public class JsonValidatorImpl implements JsonValidator {

    @Override
    public boolean validate(char[] json, Schema schema) {
        // todo remember about required, case when boolean is required and it's all json
        try {
            int i = validate(json, 0, schema);
            for (; i < json.length; i++) {
                char character = json[i];
                if (!isWhiteSpace(character))
                    return false;
            }
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private int validate(char[] json, int i, Schema schema) throws ValidationException {
        return switch (schema.getType()) {
            case BOOLEAN -> validateBoolean(json, i);
            case INTEGER -> validateInteger(json, i, schema);
            case DOUBLE -> -1;
            case STRING -> -1;
            case OBJECT -> validateObject(json, i, schema);
            case ARRAY -> -1;
            case WHITESPACE -> -1;
            case NULL -> -1;
        };
    }

    /**
     * @param json which we validate
     * @param i    index from we should start validation
     * @return index of the first char after the boolean
     * @throws ValidationException if JSON is invalid
     */
    private int validateBoolean(char[] json, int i) throws ValidationException {
        for (; i < json.length; i++) {
            char c1 = json[i];
            if (!isWhiteSpace(c1)) {
                if (c1 == 'f' && json[i + 1] == 'a' && json[i + 2] == 'l' && json[i + 3] == 's' && json[i + 4] == 'e')
                    return i + 5;
                else if (c1 == 't' && json[i + 1] == 'r' && json[i + 2] == 'u' && json[i + 3] == 'e')
                    return i + 4;
                else
                    throw new ValidationException("Boolean is invalid");
            }
        }
        throw new ValidationException("Boolean is empty");
    }

    /**
     * @param json which we validate
     * @param i    index from we should start validation
     * @return index of the first char after the integer
     * @throws ValidationException if JSON is invalid
     */
    private int validateInteger(char[] json, int i, IntegerSchema schema) throws ValidationException {
        for (; i < json.length; i++) {
            if (!isWhiteSpace(json[i]))
                break;
        }

        var charArray = new TCharArrayList();
        char character = json[i++];
        if (character == '-' || (character >= '0' && character <= '9'))
            charArray.add(character);

        while (i < json.length) {
            character = json[i++];
            if (character >= '0' && character <= '9') {
                charArray.add(character);
            } else if (isWhiteSpace(character)) {
                String stringNumber = new String(charArray.toArray());
                int number = Integer.parseInt(stringNumber);
                Double max = schema.getMax();
                if (max != null && number > max) {
                    throw new ValidationException("Integer can't be higher than " + max);
                }
                Double min = schema.getMin();
                if (min != null && number < min) {
                    throw new ValidationException("Integer can't be lower than " + min);
                }
                return i;
            } else {
                throw new ValidationException("Invalid number");
            }
        }
        throw new ValidationException("Int is empty");
    }


    /**
     * STEPS: <br/>
     * 1. Should find { char <br/>
     * 2. Should find " char. If found } char checks that object have not any required parameters <br/>
     * 3. We create a string from the following character until " appears (keeping in mind the masking) <br/>\
     * 4. Checks that the object does not have two fields with the same key <br/>
     * 5. Should find : char <br/>
     * 6. Validate JSON value
     * 7. If found , char then go to step 2. If found }, return index of next char.
     * In each step we skip empty chars. If we found something different from what we were looking for we throw exception
     *
     * @param json   which we validate
     * @param i      index from we should start validation
     * @param schema on the basis of which we validate
     * @return index of the first char after the object
     * @throws ValidationException if JSON is invalid
     */
    private int validateObject(char[] json, int i, ObjectSchema schema) throws ValidationException {
        int requiredPropertiesCount = schema.getRequiredPropertiesCount();

        // 1 step
        while (i < json.length) {
            char character = json[i++];
            if (!isWhiteSpace(character)) {
                if (character == '{') {
                    break;
                } else {
                    throw new ValidationException("Object doesn't begin with { char");
                }
            }
        }
        Set<String> processedFields = new HashSet<>(schema.getPropertiesCount() + 1, 1);
        for (; i < json.length; i++) {
            // 2 step
            while (i < json.length) {
                char character = json[i++];
                if (!isWhiteSpace(character)) {
                    if (character == '"') {
                        break;
                    } else if (character == '}') {
                        if (requiredPropertiesCount == 0)
                            return i;
                        else
                            throw new ValidationException("Object is empty and doesn't contain required fields");
                    } else {
                        throw new ValidationException("Object key doesn't begin with \" char");
                    }
                }
            }
            // 3 step
            var charArray = new TCharLinkedList();
            while (i < json.length) {
                char character = json[i++];
                if (character == '\\')
                    charArray.add(json[++i]);
                else if (character == '"') {
                    break;
                } else
                    charArray.add(character);
            }
            // step 4
            var key = new String(charArray.toArray());
            if (processedFields.contains(key)) {
                throw new ValidationException(key + " field is duplicated in object");
            }
            processedFields.add(key);
            // step 5
            while (i < json.length) {
                char character = json[i++];
                if (!isWhiteSpace(character)) {
                    if (character == ':') {
                        break;
                    } else {
                        String message = String.format("After %s key should be : char instead %s", key, character);
                        throw new ValidationException(message);
                    }
                }
            }
            // step 6
            var propertySchema = schema.getProperty(key);
            if (propertySchema == null) {
                throw new ValidationException();
            }
            i = validate(json, i, propertySchema);
            // step 7
            while (i < json.length) {
                char character = json[i++];
                if (!isWhiteSpace(character)) {
                    if (character == ',')
                        break;
                    if (character == '}') {
                        if (processedFields.size() != requiredPropertiesCount) {
                            throw new ValidationException("All required fields have to be declared");
                        }
                        return i;
                    }
                }
            }
        }
        throw new ValidationException("Object doesn't end properly");
    }

    private boolean isWhiteSpace(char c) {
        return c == ' ' || c == '\n';
    }
}