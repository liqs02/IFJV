/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validators;

import com.patryklikus.IFJV.library.schemas.models.StringSchema;
import com.patryklikus.IFJV.library.utils.CharUtils;
import gnu.trove.list.linked.TCharLinkedList;

class JsonStringValidatorImpl implements JsonStringValidator {
    @Override
    public int validate(char[] json, int i, StringSchema schema) throws JsonValidationException {
        while (i < json.length) {
            char character = json[i++];
            if (!CharUtils.isWhiteSpace(character)) {
                int[] indexPointer = new int[1];
                indexPointer[0] = i - 1;
                validateLength(CharUtils.extractString(json, indexPointer), schema, --i);
                System.out.println(json[indexPointer[0]]);
                return indexPointer[0];
            }
        }
        throw new JsonValidationException("Empty value", --i);
    }

    private void validateLength(TCharLinkedList charList, StringSchema schema, int beginIndex) throws JsonValidationException {
        int length = charList.size();
        int maxLength = schema.getMaxLength();
        if (length > maxLength)
            throw new JsonValidationException("String length can't be higher than " + maxLength, beginIndex);
        int minLength = schema.getMinLength();
        if (length < minLength)
            throw new JsonValidationException("String length can't be lower than " + minLength, beginIndex);
    }
}