/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.ObjectSchema;
import com.patryklikus.ifjv.schemas.Schema;
import com.patryklikus.ifjv.utils.CharUtils;
import gnu.trove.list.linked.TCharLinkedList;
import java.util.HashSet;
import java.util.Set;

class JsonObjectValidatorImpl implements JsonObjectValidator {
    private final JsonValidatorImpl jsonValidator;

    public JsonObjectValidatorImpl(JsonValidatorImpl jsonValidator) {
        this.jsonValidator = jsonValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int validateObject(char[] json, int i, ObjectSchema schema) throws ValidationException {
        // 1 step
        while (i < json.length) {
            char character = json[i++];
            if (!CharUtils.isWhiteSpace(character)) {
                if (character == '{') {
                    break;
                } else {
                    throw new ValidationException("Object doesn't begin with {", --i);
                }
            }
        }
        // step 2
        int[] indexPointer = new int[1];
        TCharLinkedList extractedString = null;
        int processedRequiredProps = 0;
        for (; i < json.length; i++) {
            char character = json[i];
            if (!CharUtils.isWhiteSpace(character)) {
                if (character == '}') {
                    if (schema.getPropertiesCount() == 0)
                        return ++i;
                    else
                        throw new ValidationException("Object is empty and doesn't contain required fields", i);
                } else {
                    break;
                }
            }
        }
        Set<String> processedFields = new HashSet<>(schema.getPropertiesCount() + 1, 1);
        for (; i < json.length; i++) {
            // step 3
            while (i < json.length) {
                char character = json[i++];
                if (!CharUtils.isWhiteSpace(character)) {
                    if (character == '"') {
                        indexPointer[0] = i - 1;
                        extractedString = CharUtils.extractString(json, indexPointer);
                        i = indexPointer[0];
                        break;
                    } else {
                        throw new ValidationException("Invalid key string", --i);
                    }
                }
            }
            if (extractedString == null)
                throw new ValidationException("Object doesn't end properly", --i);
            // step 4
            var key = new String(extractedString.toArray());
            if (processedFields.contains(key)) {
                throw new ValidationException(key + " field is duplicated in object", --i);
            }
            // step 5
            while (i < json.length) {
                char character = json[i++];
                if (!CharUtils.isWhiteSpace(character)) {
                    if (character == ':') {
                        break;
                    } else {
                        throw new ValidationException("Unexpected character", --i);
                    }
                }
            }
            // step 6
            Schema propertySchema = schema.getProperty(key);
            processedFields.add(key);
            if (propertySchema == null) {
                throw new ValidationException("Object has a prohibited field", --i);
            }
            i = jsonValidator.validate(json, i, propertySchema);
            if (propertySchema.isRequired())
                processedRequiredProps++;
            // step 7
            while (i < json.length) {
                char character = json[i++];
                if (!CharUtils.isWhiteSpace(character)) {
                    if (character == ',')
                        break;
                    if (character == '}') {
                        if (schema.getRequiredPropertiesCount() != processedRequiredProps) {
                            throw new ValidationException("All required fields have to be declared in object", --i);
                        }
                        return i;
                    }
                }
            }
        }
        throw new ValidationException("Object doesn't end properly", --i);
    }
}