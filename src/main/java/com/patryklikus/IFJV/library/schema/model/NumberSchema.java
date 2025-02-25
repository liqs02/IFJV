/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schema.model;

import com.patryklikus.IFJV.library.util.JsonElementType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@EqualsAndHashCode
@Getter
@ToString
public class NumberSchema implements JsonSchema {
    private final double minimum;
    private final double maximum;

    public NumberSchema(Double minimum, Double maximum, Double exclusiveMinimum, Double exclusiveMaximum) {
        if (minimum != null && exclusiveMinimum != null)
            throw new IllegalArgumentException("minimum and exclusiveMinimum can't be defined together");
        if (exclusiveMinimum != null)
            minimum = Math.nextUp(exclusiveMinimum);
        if (maximum != null && exclusiveMaximum != null)
            throw new IllegalArgumentException("maximum and exclusiveMaximum can't be defined together");
        if (exclusiveMaximum != null)
            maximum = Math.nextDown(exclusiveMaximum);
        this.minimum = Objects.requireNonNullElse(minimum, Double.MIN_VALUE);
        this.maximum = Objects.requireNonNullElse(maximum, Double.MAX_VALUE);
        if (this.minimum >= this.maximum)
            throw new IllegalArgumentException("minimum value can't be higher than maximum");
    }

    @Override
    public JsonElementType getType() {
        return JsonElementType.NUMBER;
    }
}
