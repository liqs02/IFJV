/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schemas.deserializers;

import com.google.gson.*;
import com.patryklikus.IFJV.library.schemas.models.StringSchema;
import com.patryklikus.IFJV.library.utils.GsonUtils;
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
