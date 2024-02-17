/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.models.ArraySchema;

public interface JsonArrayValidator extends JsonElementValidator {
    /**
     * @param json  which we validate
     * @param index from we should start validation
     * @return index of the first char after the array
     * @throws JsonValidationException if JSON is invalid
     */
    int validate(char[] json, int index, ArraySchema schema) throws JsonValidationException;
}
