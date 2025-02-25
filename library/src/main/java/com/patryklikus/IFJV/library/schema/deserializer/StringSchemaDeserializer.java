/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schema.deserializer;

import com.google.gson.*;
import com.patryklikus.IFJV.library.schema.model.StringSchema;
import com.patryklikus.IFJV.library.util.GsonUtils;

import java.lang.reflect.Type;

public class StringSchemaDeserializer implements JsonDeserializer<StringSchema> {
    @Override
    public StringSchema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = GsonUtils.getObjectSafe(json);
        if (jsonObject == null)
            throw new JsonParseException("unexpected null json");

        return new StringSchema(
                GsonUtils.getIntegerMemberSafe(jsonObject, "minLength"),
                GsonUtils.getIntegerMemberSafe(jsonObject, "maxLength")
        );
    }
}
