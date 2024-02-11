/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

public class ValidationException extends Exception {
    /**
     * @param message begin with uppercase, ends without any space, comma, or dot.
     */
    public ValidationException(String message, int errorIndex) {
        super(message + " at index " + errorIndex);
    }
}
