/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schema.deserializer;

import com.google.gson.*;
import com.patryklikus.IFJV.library.schema.model.*;
import com.patryklikus.IFJV.library.util.GsonUtils;
import com.patryklikus.IFJV.library.util.JsonElementType;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.Type;
import java.util.Objects;

public class SchemaDeserializer implements JsonDeserializer<JsonSchema> {
    private static final Logger LOG = LoggerFactory.getLogger(SchemaDeserializer.class);
    private final Gson gson;
    private final Yaml yaml;

    public SchemaDeserializer() {
        gson = new GsonBuilder()
                .registerTypeAdapter(ArraySchema.class, new ArraySchemaDeserializer())
                .registerTypeAdapter(IntegerSchema.class, new IntegerSchemaDeserializer())
                .registerTypeAdapter(NumberSchema.class, new NumberSchemaDeserializer())
                .registerTypeAdapter(ObjectSchema.class, new ObjectSchemaDeserializer())
                .registerTypeAdapter(StringSchema.class, new StringSchemaDeserializer())
                .registerTypeAdapter(JsonSchema.class, this)
                .create();
        yaml = new Yaml();
    }

    @Override
    public JsonSchema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return deserialize(json);
    }

    public JsonSchema deserialize(@NonNull String yamlSchema) throws InvalidJsonSchemaException {
        try {
            Object loadedYaml = yaml.load(yamlSchema);
            JsonElement schema = gson.toJsonTree(loadedYaml);
            return deserialize(schema);
        } catch (RuntimeException e) {
            LOG.warn("Invalid schema", e);
            throw new InvalidJsonSchemaException(e);
        }
    }

    private JsonSchema deserialize(@NonNull JsonElement schema) {
        JsonObject schemaObject = justTypeToObject(schema);
        String type = GsonUtils.getStringMemberSafe(schemaObject, "type");

        assert type != null;
        return switch (JsonElementType.valueOf(type.toUpperCase())) {
            case ARRAY -> gson.fromJson(schemaObject, ArraySchema.class);
            case BOOLEAN -> gson.fromJson(schemaObject, BooleanSchema.class);
            case INTEGER -> gson.fromJson(schemaObject, IntegerSchema.class);
            case NUMBER -> gson.fromJson(schemaObject, NumberSchema.class);
            case OBJECT -> gson.fromJson(schemaObject, ObjectSchema.class);
            case STRING -> gson.fromJson(schemaObject, StringSchema.class);
        };
    }

    /**
     * If just type is provided (string json), creates correct JsonObject for deserializers.
     *
     * @return JsonObject with correct type field
     */
    private JsonObject justTypeToObject(@NonNull JsonElement schema) {
        JsonObject schemaObject = GsonUtils.getObjectSafe(schema);
        String typeInString;
        if (schemaObject == null) { // if just type is provided
            schemaObject = new JsonObject();
            typeInString = schema.getAsString();
        } else if (schema.isJsonObject()) {
            typeInString = GsonUtils.getStringMemberSafe(schemaObject, "type");
            if (typeInString == null)
                throw new IllegalArgumentException("type isn't defined");
        } else {
            throw new IllegalArgumentException("type isn't defined");
        }

        if (!Objects.equals(typeInString, typeInString.toLowerCase()))
            throw new IllegalArgumentException("type has to be lowercase");

        schemaObject.addProperty("type", typeInString);
        return schemaObject;
    }
}
