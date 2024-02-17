/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.models.StringSchema;

public interface JsonStringValidator extends JsonElementValidator {
    /**
     * @param json  which we validate
     * @param index from we should start validation
     * @return index of the first char after the string
     * @throws JsonValidationException if JSON is invalid
     */
    int validate(char[] json, int index, StringSchema schema) throws JsonValidationException;
}
