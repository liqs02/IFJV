/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validators;

import com.patryklikus.IFJV.library.schemas.models.ArraySchema;
import com.patryklikus.IFJV.library.utils.CharUtils;

class JsonArrayValidatorImpl implements JsonElementValidator<ArraySchema> {
    private final JsonValidator jsonValidator;

    public JsonArrayValidatorImpl(JsonValidator jsonValidator) {
        this.jsonValidator = jsonValidator;
    }

    @Override
    public int validate(String json, int i, ArraySchema schema) throws JsonValidationException {
        while (i < json.length()) {
            char character = json.charAt(i++);
            if (!CharUtils.isWhiteSpace(character)) {
                if (character == '[')
                    break;
                else
                    throw new JsonValidationException("Array should begin with [ character", --i);
            }
        }

        for (; i < json.length(); i++) {
            char character = json.charAt(i);
            if (!CharUtils.isWhiteSpace(character)) {
                if (character == ']') {
                    if (schema.getMinItems() > 0) {
                        throw new JsonValidationException("Array is empty but should contain at least " + schema.getMinItems() + " items", i);
                    } else {
                        return ++i;
                    }
                } else {
                    break;
                }
            }
        }

        int size = 1;
        for (; i < json.length(); i++, size++) {
            if (schema.getMaxItems() < size) {
                throw new JsonValidationException("Array should contain at most " + schema.getMaxItems() + " items", i);
            }
            i = jsonValidator.validate(json, i, schema.getItems());
            while (i < json.length()) {
                char character = json.charAt(i++);
                if (!CharUtils.isWhiteSpace(character)) {
                    if (character == ',')
                        break;
                    else if (character == ']')
                        return i;
                    else
                        throw new JsonValidationException("Array should end with ] character", --i);
                }
            }
        }
        throw new JsonValidationException("Array should end with ] character", --i);
    }
}