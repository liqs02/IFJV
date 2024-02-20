/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validators;

import com.patryklikus.IFJV.library.utils.CharUtils;

class JsonBooleanValidatorImpl implements JsonBooleanValidator {
    @Override
    public int validate(String json, int i) throws JsonValidationException {
        while (i < json.length()) {
            char character = json.charAt(i++);
            if (!CharUtils.isWhiteSpace(character)) {
                if (character == 'f' && json.charAt(i) == 'a' && json.charAt(i + 1) == 'l' && json.charAt(i + 2) == 's' && json.charAt(i + 3) == 'e')
                    return i + 4;
                else if (character == 't' && json.charAt(i) == 'r' && json.charAt(i + 1) == 'u' && json.charAt(i + 2) == 'e')
                    return i + 3;
                else
                    throw new JsonValidationException("Boolean is invalid", --i);
            }
        }
        throw new JsonValidationException("Boolean is empty", --i);
    }
}