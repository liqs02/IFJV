/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class SchemaExtractorImplTestCases {
    /**
     * Tests which validate extracting schema when we define only {@code type: x}
     */
    static final String EXTRACT_DEFAULT_SCHEMA_TEST = "Should extract default schema";
    /**
     * Should extract schema with defined optional properties
     */
    static final String EXTRACT_SCHEMA_TEST = "Should extract schema with optional properties";
    /**
     * Should extract schema with defined optional properties
     */
    static final String EXTRACT_SCHEMA_WITH_ILLEGAL_VALUES_TEST = "Should throw InvalidJsonSchemaException";
    private final SchemaExtractor schemaExtractor;

    SchemaExtractorImplTestCases(SchemaExtractor schemaExtractor) {
        this.schemaExtractor = schemaExtractor;
    }

    /**
     * {@link SchemaExtractorImplTestCases#EXTRACT_SCHEMA_WITH_ILLEGAL_VALUES_TEST} util
     */
    void extractSchemaWithIllegalValuesTest(String schema) {
        var exception = assertThrows(InvalidJsonSchemaException.class, () -> schemaExtractor.extract(schema));
        assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
    }
}
