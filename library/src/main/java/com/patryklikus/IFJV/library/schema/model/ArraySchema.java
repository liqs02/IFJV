/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schema.model;

import com.patryklikus.IFJV.library.util.JsonElementType;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class ArraySchema implements JsonSchema {
    private final int minItems;
    private final int maxItems;
    private final JsonSchema items;

    public ArraySchema(Integer minItems, Integer maxItems, JsonSchema items) {
        minItems = Objects.requireNonNullElse(minItems, 0);
        maxItems = Objects.requireNonNullElse(maxItems, Integer.MAX_VALUE);
        if (minItems < 0)
            throw new IllegalArgumentException("minItems must be a non-negative integer");
        if (maxItems < 0)
            throw new IllegalArgumentException("maxItems must be a non-negative integer");
        if (minItems > maxItems)
            throw new IllegalArgumentException("maxItems can't be less or equal to minItems");
        this.minItems = minItems;
        this.maxItems = maxItems;
        if (items == null)
            throw new IllegalArgumentException("items must be defined");
        this.items = items;
    }

    @Override
    public JsonElementType getType() {
        return JsonElementType.ARRAY;
    }
}
