/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validators;

import com.patryklikus.IFJV.library.schemas.models.StringSchema;

public interface JsonStringValidator extends JsonElementValidator {
    /**
     * @param json  which we validate
     * @param index from we should start validation
     * @return index of the first char after the string
     * @throws JsonValidationException if JSON is invalid
     */
    int validate(String json, int index, StringSchema schema) throws JsonValidationException;
}
