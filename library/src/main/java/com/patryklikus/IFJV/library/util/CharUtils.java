/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.util;

import com.patryklikus.IFJV.library.validator.JsonValidationException;
import gnu.trove.list.linked.TCharLinkedList;

public final class CharUtils {
    private CharUtils() {
    }

    /**
     * Should find " char. Create a string from the following character until " appears (keeping in mind the masking)
     *
     * @param index array with one value (index) which will be updated
     * @return extracted string as char linked list
     */
    public static TCharLinkedList extractString(String json, int[] index) throws JsonValidationException {
        var charList = new TCharLinkedList();
        int i = index[0];

        while (i < json.length()) {
            char character = json.charAt(i++);
            if (!CharUtils.isWhiteSpace(character)) {
                if (character == '"')
                    break;
                else
                    throw new JsonValidationException("String doesn't begin with \" char", --i);
            }
        }

        while (i < json.length()) {
            char character = json.charAt(i++);
            if (character == '\\') {
                character = json.charAt(++i);
                charList.add(character);
            } else if (character == '"') {
                index[0] = i;
                return charList;
            } else {
                charList.add(character);
            }
        }

        throw new JsonValidationException("String doesn't end with \" char", --i);
    }

    public static boolean isWhiteSpace(char character) {
        return character == ' ' || character == '\n';
    }

    public static boolean isDigit(char character) {
        return character >= '0' && character <= '9';
    }

    public static boolean isNotDigit(char character) {
        return character < '0' || character > '9';
    }
}
