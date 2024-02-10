/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.CharUtils;
import com.patryklikus.ifjv.schemas.ObjectSchema;
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
        int requiredPropertiesCount = schema.getRequiredPropertiesCount();

        // 1 step
        while (i < json.length) {
            char character = json[i++];
            if (!CharUtils.isWhiteSpace(character)) {
                if (character == '{') {
                    break;
                } else {
                    throw new ValidationException("Object doesn't begin with { char");
                }
            }
        }
        Set<String> processedFields = new HashSet<>(schema.getPropertiesCount() + 1, 1);
        int[] indexPointer = new int[1];
        TCharLinkedList extractedString = null;
        for (; i < json.length; i++) {
            // step 2
            while (i < json.length) {
                char character = json[i++];
                if (!CharUtils.isWhiteSpace(character)) {
                    if (character == '}') {
                        if (requiredPropertiesCount == 0)
                            return i;
                        else
                            throw new ValidationException("Object is empty and doesn't contain required fields");
                    } else {
                        indexPointer[0] = i;
                        extractedString = CharUtils.extractString(json, indexPointer);
                        i = indexPointer[0];
                        break;
                    }
                }
            }
            if (extractedString == null)
                throw new ValidationException("Object doesn't end properly");
            // step 3
            var key = new String(extractedString.toArray());
            if (processedFields.contains(key)) {
                throw new ValidationException(key + " field is duplicated in object");
            }
            processedFields.add(key);
            // step 4
            while (i < json.length) {
                char character = json[i++];
                if (!CharUtils.isWhiteSpace(character)) {
                    if (character == ':') {
                        break;
                    } else {
                        String message = String.format("After %s key should be : char instead %s", key, character);
                        throw new ValidationException(message);
                    }
                }
            }
            // step 5
            var propertySchema = schema.getProperty(key);
            if (propertySchema == null) {
                throw new ValidationException();
            }
            i = jsonValidator.validate(json, i, propertySchema);
            // step 6
            while (i < json.length) {
                char character = json[i++];
                if (!CharUtils.isWhiteSpace(character)) {
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
}