/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv;

import com.patryklikus.ifjv.validators.ValidationException;
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
    public static TCharLinkedList extractString(char[] json, int[] index) throws ValidationException {
        var charList = new TCharLinkedList();
        int i = index[0];

        while (i < json.length) {
            char character = json[i++];
            if (!CharUtils.isWhiteSpace(character)) {
                if (character == '"')
                    break;
                else
                    throw new ValidationException("String doesn't begin with \" char");
            }
        }

        while (i < json.length) {
            char character = json[i++];
            if (character == '\\') {
                character = json[++i];
                charList.add(character);
            } else if (character == '"') {
                index[0] = i;
                return charList;
            } else {
                charList.add(character);
            }
        }

        throw new ValidationException("String doesn't end with \" char");
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
