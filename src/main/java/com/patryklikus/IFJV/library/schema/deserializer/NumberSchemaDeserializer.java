/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schema.deserializer;

import com.google.gson.*;
import com.patryklikus.IFJV.library.schema.model.NumberSchema;
import com.patryklikus.IFJV.library.util.GsonUtils;

import java.lang.reflect.Type;

public class NumberSchemaDeserializer implements JsonDeserializer<NumberSchema> {
    @Override
    public NumberSchema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = GsonUtils.getObjectSafe(json);
        if (jsonObject == null)
            throw new JsonParseException("unexpected null json");

        return new NumberSchema(
                GsonUtils.getDoubleMemberSafe(jsonObject, "minimum"),
                GsonUtils.getDoubleMemberSafe(jsonObject, "maximum"),
                GsonUtils.getDoubleMemberSafe(jsonObject, "exclusiveMinimum"),
                GsonUtils.getDoubleMemberSafe(jsonObject, "exclusiveMaximum")
        );
    }
}
