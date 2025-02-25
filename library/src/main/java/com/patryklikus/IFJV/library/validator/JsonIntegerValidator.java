/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validator;

import com.patryklikus.IFJV.library.schema.model.IntegerSchema;
import com.patryklikus.IFJV.library.util.CharUtils;
import gnu.trove.list.linked.TCharLinkedList;
import lombok.NonNull;

class JsonIntegerValidator implements JsonElementValidator<IntegerSchema> {
    @Override
    public int validate(String json, int i, @NonNull IntegerSchema schema) throws JsonValidationException {
        while (i < json.length() && CharUtils.isWhiteSpace(json.charAt(i)))
            i++;

        var charList = new TCharLinkedList();
        char character = json.charAt(i++);
        if (character == '-' || CharUtils.isDigit(character))
            charList.add(character);
        else
            throw new JsonValidationException("illegal character", --i);

        while (i < json.length()) {
            character = json.charAt(i++);
            if (CharUtils.isDigit(character)) {
                charList.add(character);
            } else if (CharUtils.isWhiteSpace(character) || character == ',' || character == '}') {
                validateRange(charList, schema, --i);
                return i;
            } else {
                throw new JsonValidationException("Invalid integer", --i);
            }
        }
        throw new JsonValidationException("Int is empty", --i);
    }

    private void validateRange(TCharLinkedList digits, IntegerSchema schema, int beginIndex) throws JsonValidationException {
        String stringNumber = new String(digits.toArray());
        long number = Long.parseLong(stringNumber);
        long maximum = schema.getMaximum();
        if (number > maximum)
            throw new JsonValidationException("Integer can't be higher than " + maximum, beginIndex);
        long minimum = schema.getMinimum();
        if (number < minimum)
            throw new JsonValidationException("Integer can't be lower than " + minimum, beginIndex);
    }
}