/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schemas.models;

import com.patryklikus.IFJV.library.utils.JsonElementType;
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
