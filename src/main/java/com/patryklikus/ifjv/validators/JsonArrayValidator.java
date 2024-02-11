/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.ArraySchema;

public interface JsonArrayValidator {
    /**
     * @param json  which we validate
     * @param index from we should start validation
     * @return index of the first char after the array
     * @throws ValidationException if JSON is invalid
     */
    int validateArray(char[] json, int index, ArraySchema schema) throws ValidationException;
}
