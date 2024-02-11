/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.ArraySchema;
import com.patryklikus.ifjv.utils.CharUtils;

class JsonArrayValidatorImpl implements JsonArrayValidator {
    private final JsonValidatorImpl jsonValidator;

    public JsonArrayValidatorImpl(JsonValidatorImpl jsonValidator) {
        this.jsonValidator = jsonValidator;
    }

    @Override
    public int validateArray(char[] json, int i, ArraySchema schema) throws ValidationException {
        while (i < json.length) {
            char character = json[i++];
            if (!CharUtils.isWhiteSpace(character)) {
                if (character == '[')
                    break;
                else
                    throw new ValidationException("Array should begin with [ character", --i);
            }
        }

        for (; i < json.length; i++) {
            char character = json[i];
            if (!CharUtils.isWhiteSpace(character)) {
                if (character == ']')
                    if (schema.getMinSize() != null && schema.getMinSize() > 0) {
                        throw new ValidationException("Array is empty but should contain at least " + schema.getMinSize() + " elements", i);
                    } else {
                        break;
                    }
            }
        }

        int size = 1;
        for (; i < json.length; i++, size++) {
            if (schema.getMaxSize() != null && schema.getMaxSize() < size) {
                throw new ValidationException("Array should contain at most " + schema.getMaxSize() + " elements", i);
            }
            i = jsonValidator.validate(json, i, schema.getItems());
            while (i < json.length) {
                char character = json[i++];
                if (!CharUtils.isWhiteSpace(character)) {
                    if (character == ',')
                        break;
                    else if (character == ']')
                        return i;
                    else
                        throw new ValidationException("Array should end with ] character", --i);
                }
            }
        }
        throw new ValidationException("Array should end with ] character", --i);
    }
}