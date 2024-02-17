/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas.models;

import com.patryklikus.ifjv.utils.JsonDataType;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class IntegerSchema implements JsonSchema {
    private final long minimum;
    private final long maximum;

    public IntegerSchema(Long minimum, Long maximum, Long exclusiveMinimum, Long exclusiveMaximum) {
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
        if (this.minimum >= this.maximum)
            throw new IllegalArgumentException("Minimum value can't be higher than maximum");
    }

    @Override
    public JsonDataType getType() {
        return JsonDataType.INTEGER;
    }
}
