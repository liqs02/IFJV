/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validator;

import com.patryklikus.IFJV.library.schema.model.StringSchema;
import com.patryklikus.IFJV.library.util.CharUtils;
import gnu.trove.list.linked.TCharLinkedList;

class JsonStringValidator implements JsonElementValidator<StringSchema> {
    @Override
    public int validate(String json, int i, StringSchema schema) throws JsonValidationException {
        while (i < json.length()) {
            char character = json.charAt(i++);
            if (!CharUtils.isWhiteSpace(character)) {
                int[] indexPointer = new int[1];
                indexPointer[0] = i - 1;
                validateLength(CharUtils.extractString(json, indexPointer), schema, --i);
                System.out.println(json.charAt(indexPointer[0]));
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