/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.models.JsonSchema;
import lombok.NonNull;

public interface JsonValidator {
    /**
     * @return error message, if null then JSON is valid.
     */
    String validate(char[] json, @NonNull JsonSchema schema);

    default String validate(String json, @NonNull JsonSchema schema) {
        return validate(json.toCharArray(), schema);
    }
}
