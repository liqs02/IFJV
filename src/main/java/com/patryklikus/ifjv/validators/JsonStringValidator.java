/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.models.StringSchema;

public interface JsonStringValidator {
    // todo add description that we end validation of end of string all where, and that we don't check required here
    /**
     * @param json  which we validate
     * @param index from we should start validation
     * @return index of the first char after the string
     * @throws JsonValidationException if JSON is invalid
     */
    int validate(char[] json, int index, StringSchema schema) throws JsonValidationException;
}
