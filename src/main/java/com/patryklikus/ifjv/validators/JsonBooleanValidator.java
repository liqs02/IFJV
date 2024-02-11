/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

public interface JsonBooleanValidator {
    /**
     * @param json  which we validate
     * @param index from we should start validation
     * @return index of the first char after the boolean
     * @throws ValidationException if JSON is invalid
     */
    int validateBoolean(char[] json, int index) throws ValidationException;
}
