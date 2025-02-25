/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validator;

import com.patryklikus.IFJV.library.schema.model.JsonSchema;
import com.patryklikus.IFJV.library.util.JsonElementType;

/**
 * Represents classes which validates some type of {@link JsonElementType}.
 * Each class has methods to validate data of this type.
 * These methods return first index after validated element (it doesn't check what is next, it's role of
 * {@link JsonValidator}).
 */
public interface JsonElementValidator<T extends JsonSchema> {
    /**
     * @param json  which we validate
     * @param index from we should start validation
     * @return index of the first char after the validated json element
     * @throws JsonValidationException if JSON is invalid
     */
    int validate(String json, int index, T schema) throws JsonValidationException;
}
