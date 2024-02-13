/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.patryklikus.ifjv.schemas.models.*;
import com.patryklikus.ifjv.utils.JsonDataType;
import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaExtractorImpl implements SchemaExtractor {
    private static final Logger LOG = LoggerFactory.getLogger(SchemaExtractorImpl.class);
    private final ObjectMapper yamlMapper;

    public SchemaExtractorImpl() {
        yamlMapper = JsonMapper.builder(new YAMLFactory())
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
    }

    public JsonSchema extract(String yamlSchema) throws InvalidJsonSchemaException {
        try {
            JsonNode schema = yamlMapper.readTree(yamlSchema);
            return extract(schema);
        } catch (JsonProcessingException e) {
            LOG.warn("Invalid YAML schema", e);
            throw new InvalidJsonSchemaException(e);
        }
    }

    private JsonSchema extract(@NonNull JsonNode schema) {
        return switch (yamlMapper.convertValue(schema.get("type"), JsonDataType.class)) {
            case BOOLEAN -> yamlMapper.convertValue(schema, BooleanSchema.class);
            case INTEGER -> yamlMapper.convertValue(schema, IntegerSchema.class);
            case NUMBER -> yamlMapper.convertValue(schema, NumberSchema.class);
            case STRING -> yamlMapper.convertValue(schema, StringSchema.class);
            case OBJECT -> extractObjectSchema(schema);
            case ARRAY -> yamlMapper.convertValue(schema, ArraySchema.class);
        };
    }

    private ObjectSchema extractObjectSchema(@NonNull JsonNode schema) {
        var properties = yamlMapper.convertValue(schema.get("properties"), new TypeReference<Map<String, JsonNode>>() {
        });
        var propertiesSchemas = new HashMap<String, JsonSchema>();
        for (var key : properties.keySet()) {
            var value = extract(properties.get(key));
            propertiesSchemas.put(key, value);
        }
        var objectSchema = yamlMapper.convertValue(schema, ObjectSchema.class);
        objectSchema.setProperties(propertiesSchemas);
        return objectSchema;
    }
}
