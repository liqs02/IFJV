package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.Schema;
import lombok.NonNull;

import java.util.Optional;

public interface JsonValidator {
    /**
     * @return Error message, if null then JSON is valid.
     */
    Optional<String> validate(char[] json, @NonNull Schema schema);

    default Optional<String> validate(String json, @NonNull Schema schema) {
        return validate(json.toCharArray(), schema);
    }
}
