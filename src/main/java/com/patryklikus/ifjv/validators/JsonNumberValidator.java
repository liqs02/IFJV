/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.NumberSchema;
import lombok.NonNull;

public interface JsonNumberValidator {
    /**
     * @param json  which we validate
     * @param index from we should start validation
     * @return index of the first char after the integer
     * @throws ValidationException if JSON is invalid
     */
    int validateInteger(char[] json, int index, @NonNull NumberSchema schema) throws ValidationException;

    /**
     * @param json which we validate
     * @param i index from we should start validation
     * @return index of the first char after the double
     * @throws ValidationException if JSON is invalid
     */
    int validateDouble(char[] json, int i, @NonNull NumberSchema schema) throws ValidationException;
}
