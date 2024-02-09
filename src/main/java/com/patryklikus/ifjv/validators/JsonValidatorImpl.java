/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.ObjectSchema;
import com.patryklikus.ifjv.schemas.Schema;
import gnu.trove.list.linked.TCharLinkedList;

import java.util.HashSet;
import java.util.Set;

public class JsonValidatorImpl implements JsonValidator {

    @Override
    public boolean validate(char[] json, Schema schema) {
        // todo remember about required
        try {
            int i = validate(json, 0, schema);
            for (; i < json.length; i++) {
                char character = json[i];
                if (character != ' ')
                    return false;
            }
        } catch (ValidationException e) {
            return false;
        }
        return true;
    }

    private int validate(char[] json, int i, Schema schema) throws ValidationException {
        return switch (schema.getType()) {
            case INTEGER -> 0;
            case DOUBLE -> validateBoolean(json, i);
            case STRING -> 0;
            case BOOLEAN -> 0;
            case ARRAY -> 0;
            case OBJECT -> validateObject(json, i, schema);
            case WHITESPACE -> 0;
            case NULL -> 0;
        };
    }

    /**
     * STEPS: <br/>
     * 1. Should find { char <br/>
     * 2. Should find " char <br/>
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
        // 1 step
        for (; i < json.length; i++) {
            char character = json[i];
            if (character != ' ') {
                if (character == '{') {
                    break; // todo check increment and how it works
                } else {
                    throw new ValidationException("Object doesn't begin with { char");
                }
            }
        }
        Set<String> processedFields = new HashSet<>(schema.getPropertiesCount() + 1, 1);
        for (; i < json.length; i++) {
            // 2 step
            for (; i < json.length; i++) {
                char character = json[i];
                if (character != ' ') {
                    if (character == '"') {
                        i++;
                        break;
                    } else {
                        throw new ValidationException("Object key doesn't begin with \" char");
                    }
                }
            }
            // 3 step
            var charArray = new TCharLinkedList();
            for (; i < json.length; i++) {
                char character = json[i];
                if (character == '\\')
                    charArray.add(json[++i]);
                else if (character == '"') {
                    i++;
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
            for (; i < json.length; i++) {
                char character = json[i];
                if (character != ' ') {
                    if (character == ':') {
                        i++;
                        break;
                    } else {
                        String message = String.format("After %s key should be : char instead %s", key, character);
                        throw new ValidationException(message);
                    }
                }
            }

            // step 6
            var propertySchema = schema.getPropertySchema(key);
            if (propertySchema == null) {
                throw new ValidationException();
            }
            i = validate(json, i, propertySchema);

            // step 7
            for (; i < json.length; i++) {
                char character = json[i];
                if (character != ' ') {
                    if (character == ',')
                        break;
                    if (character == '}')
                        return ++i;
                }
            }
        }
        throw new ValidationException("Object doesn't end properly");
    }

    private int validateBoolean(char[] json, int i) throws ValidationException {
        for (; i < json.length; i++) {
            char character = json[i];
            if (character != ' ') {
                if (character == 'f' && json[++i] == 'a' && json[++i] == 'l' && json[++i] == 's' && json[++i] == 'e')
                    return ++i;
                else if (character == 't' && json[++i] == 'r' && json[++i] == 'u' && json[++i] == 'e')
                    return ++i;
                throw new ValidationException("Boolean is invalid"); // todo what about nulls and empties?
            }
        }
    }
}