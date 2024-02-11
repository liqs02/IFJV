/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.utils.CharUtils;

class JsonBooleanValidatorImpl implements JsonBooleanValidator {
    @Override
    public int validateBoolean(char[] json, int i) throws ValidationException {
        while (i < json.length) {
            char character = json[i++];
            if (!CharUtils.isWhiteSpace(character)) {
                if (character == 'f' && json[i] == 'a' && json[i + 1] == 'l' && json[i + 2] == 's' && json[i + 3] == 'e')
                    return i + 4;
                else if (character == 't' && json[i] == 'r' && json[i + 1] == 'u' && json[i + 2] == 'e')
                    return i + 3;
                else
                    throw new ValidationException("Boolean is invalid", --i);
            }
        }
        throw new ValidationException("Boolean is empty", --i);
    }
}