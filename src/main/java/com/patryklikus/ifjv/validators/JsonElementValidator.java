/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.utils.JsonElementType;

/**
 * Represents classes which validates some type of {@link JsonElementType}.
 * Each class has methods to validate data of this type.
 * These methods return first index after validated element (it doesn't check what is next, it's role of {@link JsonValidator}).
 */
public interface JsonElementValidator {
}
