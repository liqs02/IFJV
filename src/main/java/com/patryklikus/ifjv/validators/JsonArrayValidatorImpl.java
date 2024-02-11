/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.CharUtils;
import com.patryklikus.ifjv.schemas.ArraySchema;

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