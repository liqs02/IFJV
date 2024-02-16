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
public class NumberSchema implements JsonSchema {
    private final double minimum;
    private final double maximum;

    public NumberSchema(
            @JsonProperty("minimum") Double minimum,
            @JsonProperty("maximum") Double maximum,
            @JsonProperty("exclusiveMinimum") Double exclusiveMinimum,
            @JsonProperty("exclusiveMaximum") Double exclusiveMaximum
    ) {
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
            throw new IllegalArgumentException("Minimum value can't be higher than maximum");
    }

    @Override
    public JsonDataType getType() {
        return JsonDataType.NUMBER;
    }
}
