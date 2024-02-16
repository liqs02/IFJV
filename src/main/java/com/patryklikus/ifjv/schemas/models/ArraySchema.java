/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patryklikus.ifjv.utils.JsonDataType;
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
    @JsonIgnore
    private JsonSchema items;

    public ArraySchema(
            @JsonProperty("minItems") Integer minItems,
            @JsonProperty("maxItems") Integer maxItems
    ) {
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
    }

    @Override
    public JsonDataType getType() {
        return JsonDataType.ARRAY;
    }

    public void setItems(JsonSchema items) {
        if (items == null)
            throw new IllegalArgumentException("items must be defined");
        this.items = items;
    }
}
