/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
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

    public Schema extract(String yamlSchema) throws InvalidSchemaException {
        Schema schema;
        try {
            schema = yamlMapper.readValue(yamlSchema, Schema.class);
        } catch (JsonProcessingException e) {
            LOG.warn("Invalid YAML schema", e);
            throw new InvalidSchemaException(e);
        }
        int requiredCount = (int) schema.getProperties().values().stream()
                .filter(Schema::isRequired)
                .count();
        schema.setRequiredPropertiesCount(requiredCount);
        return schema;

    }
}
