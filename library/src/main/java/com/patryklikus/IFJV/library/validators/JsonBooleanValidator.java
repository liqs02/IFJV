/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validators;

public interface JsonBooleanValidator extends JsonElementValidator {

    int validate(char[] json, int index) throws JsonValidationException;
}
