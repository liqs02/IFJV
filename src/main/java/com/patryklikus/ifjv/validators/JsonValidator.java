/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.Schema;
import java.util.Optional;
import lombok.NonNull;

public interface JsonValidator {
    /**
     * @return Error message, if null then JSON is valid.
     */
    Optional<String> validate(char[] json, @NonNull Schema schema);

    default Optional<String> validate(String json, @NonNull Schema schema) {
        return validate(json.toCharArray(), schema);
    }
}
