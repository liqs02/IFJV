/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patryklikus.ifjv.utils.JsonDataType;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class StringSchema implements JsonSchema {
    private final int minLength;
    private final int maxLength;

    public StringSchema(
            @JsonProperty("minLength") Integer minLength,
            @JsonProperty("maxLength") Integer maxLength
    ) {
        this.minLength = Objects.requireNonNullElse(minLength, 0);
        this.maxLength = Objects.requireNonNullElse(maxLength, Integer.MAX_VALUE);
        if (this.minLength < 0)
            throw new IllegalArgumentException("minLength must be a non-negative integer");
        if (this.maxLength < 0)
            throw new IllegalArgumentException("maxLength must be a non-negative integer");
    }

    @Override
    public JsonDataType getType() {
        return JsonDataType.STRING;
    }
}
