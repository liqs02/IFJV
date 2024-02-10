/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.CharUtils;
import com.patryklikus.ifjv.schemas.StringSchema;
import gnu.trove.list.linked.TCharLinkedList;

class JsonStringValidatorImpl implements JsonStringValidator {
    @Override
    public int validateString(char[] json, int i, StringSchema schema) throws ValidationException {
        while (i < json.length) {
            char character = json[i++];
            if (!CharUtils.isWhiteSpace(character)) {
                int[] indexPointer = new int[1];
                indexPointer[0] = i;
                validateStringLength(CharUtils.extractString(json, indexPointer), schema);
                return indexPointer[0];
            }
        }
        throw new ValidationException("Empty value");
    }

    private void validateStringLength(TCharLinkedList charList, StringSchema schema) throws ValidationException {
        int length = charList.size();
        Integer maxLength = schema.getMaxLength();
        if (maxLength != null && length > maxLength)
            throw new ValidationException("String length can't be higher than " + maxLength);
        Integer minLength = schema.getMinLength();
        if (minLength != null && length < minLength)
            throw new ValidationException("String length can't be lower than " + minLength);
    }
}