/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

public class JsonValidationException extends Exception {
    /**
     * @param message begin with uppercase, ends without any space, comma, or dot.
     */
    public JsonValidationException(String message, int errorIndex) {
        super(message + " at index " + errorIndex);
    }
}
