/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.models.IntegerSchema;
import com.patryklikus.ifjv.schemas.models.NumberSchema;
import lombok.NonNull;

public interface JsonNumberValidator extends JsonElementValidator {
    /**
     * @param json  which we validate
     * @param index from we should start validation
     * @return index of the first char after the integer
     * @throws JsonValidationException if JSON is invalid
     */
    int validate(char[] json, int index, @NonNull IntegerSchema schema) throws JsonValidationException;

    /**
     * @param json which we validate
     * @param i index from we should start validation
     * @return index of the first char after the double
     * @throws JsonValidationException if JSON is invalid
     */
    int validate(char[] json, int i, @NonNull NumberSchema schema) throws JsonValidationException;
}
