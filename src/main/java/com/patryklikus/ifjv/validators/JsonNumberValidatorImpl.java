/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.NumberSchema;
import com.patryklikus.ifjv.utils.CharUtils;
import gnu.trove.list.linked.TCharLinkedList;

class JsonNumberValidatorImpl implements JsonNumberValidator {
    @Override
    public int validateInteger(char[] json, int i, NumberSchema schema) throws ValidationException {
        while (i < json.length && CharUtils.isWhiteSpace(json[i]))
            i++;

        var charList = new TCharLinkedList();
        char character = json[i++];
        if (character == '-' || CharUtils.isDigit(character))
            charList.add(character);

        while (i < json.length) {
            character = json[i++];
            if (CharUtils.isDigit(character)) {
                charList.add(character);
            } else if (CharUtils.isWhiteSpace(character) || character == ',') {
                validateIntRange(charList, schema, --i);
                return i;
            } else {
                throw new ValidationException("Invalid integer", --i);
            }
        }
        throw new ValidationException("Int is empty", --i);
    }

    @Override
    public int validateDouble(char[] json, int i, NumberSchema schema) throws ValidationException {
        for (; i < json.length; i++) {
            if (!CharUtils.isWhiteSpace(json[i]))
                break;
        }

        var charList = new TCharLinkedList();
        char character = json[i++];
        if (character == '-' || CharUtils.isDigit(character))
            charList.add(character);

        while (i < json.length) {
            character = json[i++];
            charList.add(character);
            if (character == '.') {
                break;
            } else if (CharUtils.isWhiteSpace(character) || character == ',') {
                return i;
            } else if (CharUtils.isNotDigit(character)) {
                throw new ValidationException("Invalid double", --i);
            }
        }


        while (i < json.length) {
            character = json[i++];
            if (CharUtils.isDigit(character)) {
                charList.add(character);
            } else if (CharUtils.isWhiteSpace(character) || character == ',') {
                validateDoubleRange(charList, schema, --i);
                return i;
            } else {
                throw new ValidationException("Invalid number", --i);
            }
        }

        throw new ValidationException("Double is empty", --i);
    }

    private void validateIntRange(TCharLinkedList digits, NumberSchema schema, int beginIndex) throws ValidationException {
        String stringNumber = new String(digits.toArray());
        int number = Integer.parseInt(stringNumber);
        Double max = schema.getMax();
        if (max != null && number > max)
            throw new ValidationException("Integer can't be higher than " + max.intValue(), beginIndex);
        Double min = schema.getMin();
        if (min != null && number < min)
            throw new ValidationException("Integer can't be lower than " + min.intValue(), beginIndex);
    }

    private void validateDoubleRange(TCharLinkedList digits, NumberSchema schema, int beginIndex) throws ValidationException {
        String stringNumber = new String(digits.toArray());
        double number = Double.parseDouble(stringNumber);
        Double max = schema.getMax();
        if (max != null && number > max)
            throw new ValidationException("Integer can't be higher than " + max, beginIndex);
        Double min = schema.getMin();
        if (min != null && number < min)
            throw new ValidationException("Integer can't be lower than " + min, beginIndex);
    }
}