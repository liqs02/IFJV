/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schema.deserializer;

import com.google.gson.*;
import com.patryklikus.IFJV.library.schema.model.IntegerSchema;
import com.patryklikus.IFJV.library.util.GsonUtils;

import java.lang.reflect.Type;

public class IntegerSchemaDeserializer implements JsonDeserializer<IntegerSchema> {
    @Override
    public IntegerSchema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = GsonUtils.getObjectSafe(json);
        if (jsonObject == null)
            throw new JsonParseException("unexpected null json");

        return new IntegerSchema(
                GsonUtils.getLongMemberSafe(jsonObject, "minimum"),
                GsonUtils.getLongMemberSafe(jsonObject, "maximum"),
                GsonUtils.getLongMemberSafe(jsonObject, "exclusiveMinimum"),
                GsonUtils.getLongMemberSafe(jsonObject, "exclusiveMaximum")
        );
    }
}
