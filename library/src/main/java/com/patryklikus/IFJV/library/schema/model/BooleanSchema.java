/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schema.model;

import com.patryklikus.IFJV.library.util.JsonElementType;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class BooleanSchema implements JsonSchema {
    @Override
    public JsonElementType getType() {
        return JsonElementType.BOOLEAN;
    }
}
