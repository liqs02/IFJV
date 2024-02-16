/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.patryklikus.ifjv.schemas.models.*;
import com.patryklikus.ifjv.utils.JsonDataType;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class SchemaExtractorImpl implements SchemaExtractor {
    private static final Logger LOG = LoggerFactory.getLogger(SchemaExtractorImpl.class);
    private final ObjectMapper mapper;

    public SchemaExtractorImpl() {
        mapper = JsonMapper.builder(new YAMLFactory())
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
    }

    public JsonSchema extract(String yamlSchema) throws InvalidJsonSchemaException {
        try {
            ObjectNode schema = mapper.readValue(yamlSchema, ObjectNode.class);
            return extract(schema);
        } catch (JsonProcessingException | RuntimeException e) {
            LOG.warn("Invalid schema", e);
            throw new InvalidJsonSchemaException(e);
        }
    }

    private JsonSchema extract(@NonNull ObjectNode schema) throws JsonProcessingException {
        JsonDataType type;
        if (schema.isTextual()) { // if just type is provided
            String textType = schema.textValue();
            if (!Objects.equals(textType, textType.toLowerCase()))
                throw new IllegalArgumentException("type has to be lowercase");
            type = JsonDataType.valueOf(textType.toUpperCase());
            schema = mapper.convertValue(Map.of("type", textType), ObjectNode.class);
        } else {
            type = mapper.convertValue(schema.get("type"), JsonDataType.class);
        }
        return switch (type) {
            case ARRAY -> extractArraySchema(schema);
            case BOOLEAN -> mapper.convertValue(schema, BooleanSchema.class);
            case INTEGER -> mapper.convertValue(schema, IntegerSchema.class);
            case NUMBER -> mapper.convertValue(schema, NumberSchema.class);
            case OBJECT -> extractObjectSchema(schema);
            case STRING -> mapper.convertValue(schema, StringSchema.class);
        };
    }

    private ArraySchema extractArraySchema(@NonNull ObjectNode schema) throws JsonProcessingException {
        JsonNode itemsJson = schema.get("items");
        if (itemsJson.isTextual()) {
            schema.put("type", itemsJson.textValue());
        }
        if (itemsJson == null)
            throw new IllegalArgumentException("items must be defined");
        System.out.println(itemsJson);
        JsonSchema itemsSchema = extract(schema);

        ArraySchema arraySchema = mapper.convertValue(schema, ArraySchema.class);
        arraySchema.setItems(itemsSchema);
        return arraySchema;
    }

    private ObjectSchema extractObjectSchema(@NonNull ObjectNode schema) throws JsonProcessingException {
        Map<String, ObjectNode> properties = mapper.convertValue(schema.get("properties"), new TypeReference<>() {
        });
        if (properties == null)
            throw new IllegalArgumentException("properties must be defined");

        ArrayNode dependentRequired = schema.withArray("dependentRequired");
        if (dependentRequired.isTextual()) {
            Set<String> dependentRequiredSet = Arrays.stream(dependentRequired.textValue().split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());
            schema.putPOJO("dependentRequired", dependentRequiredSet);
        }

        var propertiesSchemas = new HashMap<String, JsonSchema>();
        for (var key : properties.keySet()) {
            var value = extract(properties.get(key));
            propertiesSchemas.put(key, value);
        }
        ObjectSchema objectSchema = mapper.convertValue(schema, ObjectSchema.class);
        objectSchema.setProperties(propertiesSchemas);
        return objectSchema;
    }
}
