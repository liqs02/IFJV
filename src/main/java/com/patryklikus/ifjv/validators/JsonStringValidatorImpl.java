/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.StringSchema;
import com.patryklikus.ifjv.utils.CharUtils;
import gnu.trove.list.linked.TCharLinkedList;

class JsonStringValidatorImpl implements JsonStringValidator {
    @Override
    public int validateString(char[] json, int i, StringSchema schema) throws ValidationException {
        while (i < json.length) {
            char character = json[i++];
            if (!CharUtils.isWhiteSpace(character)) {
                int[] indexPointer = new int[1];
                indexPointer[0] = i;
                validateStringLength(CharUtils.extractString(json, indexPointer), schema, --i);
                return indexPointer[0];
            }
        }
        throw new ValidationException("Empty value", --i);
    }

    private void validateStringLength(TCharLinkedList charList, StringSchema schema, int beginIndex) throws ValidationException {
        int length = charList.size();
        Integer maxLength = schema.getMaxLength();
        if (maxLength != null && length > maxLength)
            throw new ValidationException("String length can't be higher than " + maxLength, beginIndex);
        Integer minLength = schema.getMinLength();
        if (minLength != null && length < minLength)
            throw new ValidationException("String length can't be lower than " + minLength, beginIndex);
    }
}