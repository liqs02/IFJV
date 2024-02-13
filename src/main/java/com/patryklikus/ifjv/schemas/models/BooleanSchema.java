/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas.models;

import com.patryklikus.ifjv.utils.JsonDataType;

public class BooleanSchema implements JsonSchema {
    @Override
    public JsonDataType getType() {
        return JsonDataType.BOOLEAN;
    }
}
