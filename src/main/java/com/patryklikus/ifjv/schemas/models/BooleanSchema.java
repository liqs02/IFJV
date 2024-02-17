/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas.models;

import com.patryklikus.ifjv.utils.JsonElementType;
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
