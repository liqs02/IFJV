/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.CharUtils;

class JsonBooleanValidatorImpl implements JsonBooleanValidator {
    @Override
    public int validateBoolean(char[] json, int i) throws ValidationException {
        for (; i < json.length; i++) {
            char c1 = json[i];
            if (!CharUtils.isWhiteSpace(c1)) {
                if (c1 == 'f' && json[i + 1] == 'a' && json[i + 2] == 'l' && json[i + 3] == 's' && json[i + 4] == 'e')
                    return i + 5;
                else if (c1 == 't' && json[i + 1] == 'r' && json[i + 2] == 'u' && json[i + 3] == 'e')
                    return i + 4;
                else
                    throw new ValidationException("Boolean is invalid");
            }
        }
        throw new ValidationException("Boolean is empty");
    }
}