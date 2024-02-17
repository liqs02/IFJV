/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

public interface JsonBooleanValidator {

    int validate(char[] json, int index) throws JsonValidationException;
}
