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
public class StringSchema implements JsonSchema {
    private final int minLength;
    private final int maxLength;

    public StringSchema(Integer minLength, Integer maxLength) {
        this.minLength = Objects.requireNonNullElse(minLength, 0);
        this.maxLength = Objects.requireNonNullElse(maxLength, Integer.MAX_VALUE);
        if (this.minLength < 0)
            throw new IllegalArgumentException("minLength must be a non-negative integer");
        if (this.maxLength < 0)
            throw new IllegalArgumentException("maxLength must be a non-negative integer");
    }

    @Override
    public JsonElementType getType() {
        return JsonElementType.STRING;
    }
}
