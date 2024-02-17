/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas.deserializers;

import com.google.gson.*;
import com.patryklikus.ifjv.schemas.models.JsonSchema;
import com.patryklikus.ifjv.schemas.models.ObjectSchema;
import com.patryklikus.ifjv.utils.GsonUtils;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class ObjectSchemaDeserializer implements JsonDeserializer<ObjectSchema> {
    @Override
    public ObjectSchema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = GsonUtils.getObjectSafe(json);
        if (jsonObject == null)
            throw new JsonParseException("unexpected null json");

        return new ObjectSchema(
                GsonUtils.getBooleanMemberSafe(jsonObject, "required"),
                deserializeDependentRequired(jsonObject), deserializeProperties(jsonObject, context)
        );
    }

    private Set<String> deserializeDependentRequired(JsonObject jsonObject) {
        JsonArray jsonArray = GsonUtils.getArrayMemberSafe(jsonObject, "dependentRequired");
        if (jsonArray != null) {
            return jsonObject.get("dependentRequired")
                    .getAsJsonArray()
                    .asList().stream()
                    .map(GsonUtils::getStringSafe)
                    .collect(Collectors.toSet());
        }

        String jsonString = GsonUtils.getStringMemberSafe(jsonObject, "dependentRequired");
        if (jsonString != null) {
            return Arrays.stream(jsonString.split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());
        }

        return null;
    }

    private Map<String, JsonSchema> deserializeProperties(JsonObject jsonObject, JsonDeserializationContext context) {
        Map<String, JsonSchema> properties = new HashMap<>();
        JsonObject propertiesObject = GsonUtils.getObjectMemberSafe(jsonObject, "properties");
        if (propertiesObject == null) {
            return null;
        }
        for (Entry<String, JsonElement> schemaEntry : propertiesObject.entrySet()) {
            context.deserialize(schemaEntry.getValue(), JsonSchema.class);
            properties.put(schemaEntry.getKey(), context.deserialize(schemaEntry.getValue(), JsonSchema.class));
        }
        return properties;
    }
}
