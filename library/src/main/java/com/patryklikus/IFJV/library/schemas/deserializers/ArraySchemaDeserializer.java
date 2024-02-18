/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schemas.deserializers;

import com.google.gson.*;
import com.patryklikus.IFJV.library.schemas.models.ArraySchema;
import com.patryklikus.IFJV.library.schemas.models.JsonSchema;
import com.patryklikus.IFJV.library.utils.GsonUtils;
import java.lang.reflect.Type;

public class ArraySchemaDeserializer implements JsonDeserializer<ArraySchema> {
    @Override
    public ArraySchema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = GsonUtils.getObjectSafe(json);
        if (jsonObject == null)
            throw new JsonParseException("unexpected null json");

        return new ArraySchema(
                GsonUtils.getIntegerMemberSafe(jsonObject, "minItems"),
                GsonUtils.getIntegerMemberSafe(jsonObject, "maxItems"),
                context.deserialize(jsonObject.get("items"), JsonSchema.class)
        );
    }
}
