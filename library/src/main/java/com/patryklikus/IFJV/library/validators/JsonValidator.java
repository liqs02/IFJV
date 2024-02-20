/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validators;

import com.patryklikus.IFJV.library.schemas.models.JsonSchema;
import lombok.NonNull;

public interface JsonValidator {
    /**
     * @return error message, if null then JSON is valid.
     */
    String validate(String json, @NonNull JsonSchema schema);
}
