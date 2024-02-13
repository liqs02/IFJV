/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patryklikus.ifjv.utils.JsonDataType;
import java.util.Objects;
import lombok.Getter;

@Getter
public class IntegerSchema implements JsonSchema {
    private final long minimum;
    private final long maximum;

    public IntegerSchema(
            @JsonProperty("minimum") Long minimum,
            @JsonProperty("maximum") Long maximum,
            @JsonProperty("exclusiveMinimum") Long exclusiveMinimum,
            @JsonProperty("exclusiveMaximum") Long exclusiveMaximum
    ) {
        if (minimum != null && exclusiveMinimum != null)
            throw new IllegalArgumentException("minimum and exclusiveMinimum can't be defined together");
        if (exclusiveMinimum != null)
            minimum = exclusiveMinimum + 1;
        if (maximum != null && exclusiveMaximum != null)
            throw new IllegalArgumentException("maximum and exclusiveMaximum can't be defined together");
        if (exclusiveMaximum != null)
            maximum = exclusiveMaximum - 1;
        this.minimum = Objects.requireNonNullElse(minimum, Long.MIN_VALUE);
        this.maximum = Objects.requireNonNullElse(maximum, Long.MAX_VALUE);
    }

    @Override
    public JsonDataType getType() {
        return JsonDataType.INTEGER;
    }
}
