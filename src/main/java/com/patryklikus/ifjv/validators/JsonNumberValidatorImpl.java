/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.models.IntegerSchema;
import com.patryklikus.ifjv.schemas.models.NumberSchema;
import com.patryklikus.ifjv.utils.CharUtils;
import gnu.trove.list.linked.TCharLinkedList;

class JsonNumberValidatorImpl implements JsonNumberValidator {
    @Override
    public int validate(char[] json, int i, IntegerSchema schema) throws JsonValidationException {
        while (i < json.length && CharUtils.isWhiteSpace(json[i]))
            i++;

        var charList = new TCharLinkedList();
        char character = json[i++];
        if (character == '-' || CharUtils.isDigit(character))
            charList.add(character);
        else
            throw new JsonValidationException("illegal character", --i);

        while (i < json.length) {
            character = json[i++];
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

    @Override
    public int validate(char[] json, int i, NumberSchema schema) throws JsonValidationException {
        for (; i < json.length; i++) {
            if (!CharUtils.isWhiteSpace(json[i]))
                break;
        }

        var charList = new TCharLinkedList();
        char character = json[i++];
        if (character == '-' || CharUtils.isDigit(character))
            charList.add(character);
        else
            throw new JsonValidationException("illegal character", --i);

        while (i < json.length) {
            character = json[i++];
            charList.add(character);
            if (character == '.') {
                break;
            } else if (CharUtils.isWhiteSpace(character) || character == ',' || character == '}') {
                return i;
            } else if (CharUtils.isNotDigit(character)) {
                throw new JsonValidationException("Invalid double", --i);
            }
        }

        while (i < json.length) {
            character = json[i++];
            if (CharUtils.isDigit(character)) {
                charList.add(character);
            } else if (CharUtils.isWhiteSpace(character) || character == ',' || character == '}') {
                validateRange(charList, schema, --i);
                return i;
            } else {
                throw new JsonValidationException("Invalid number", --i);
            }
        }

        throw new JsonValidationException("Double is empty", --i);
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

    private void validateRange(TCharLinkedList digits, NumberSchema schema, int beginIndex) throws JsonValidationException {
        String stringNumber = new String(digits.toArray());
        double number = Double.parseDouble(stringNumber);
        double maximum = schema.getMaximum();
        if (number > maximum)
            throw new JsonValidationException("Number can't be higher than " + maximum, beginIndex);
        double minimum = schema.getMinimum();
        if (number < minimum)
            throw new JsonValidationException("Number can't be lower than " + minimum, beginIndex);
    }
}