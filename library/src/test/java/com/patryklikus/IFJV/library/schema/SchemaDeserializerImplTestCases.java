/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schema;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.patryklikus.IFJV.library.schema.deserializer.InvalidJsonSchemaException;
import com.patryklikus.IFJV.library.schema.deserializer.SchemaDeserializer;


class SchemaDeserializerImplTestCases {
    /**
     * Tests which validate deserializing schema when we define only {@code type: x}
     */
    static final String DESERIALIZE_DEFAULT_SCHEMA_TEST = "Should deserialize default schema";
    /**
     * Should deserialize schema with defined optional properties
     */
    static final String DESERIALIZE_SCHEMA_TEST = "Should deserialize schema with optional properties";
    /**
     * Should deserialize schema with defined optional properties
     */
    static final String DESERIALIZE_SCHEMA_WITH_ILLEGAL_VALUES_TEST = "Should throw InvalidJsonSchemaException";
    private final SchemaDeserializer schemaDeserializer;

    SchemaDeserializerImplTestCases(SchemaDeserializer schemaDeserializer) {
        this.schemaDeserializer = schemaDeserializer;
    }

    /**
     * {@link SchemaDeserializerImplTestCases#DESERIALIZE_SCHEMA_WITH_ILLEGAL_VALUES_TEST} util
     */
    void deserializeSchemaWithIllegalValuesTest(String schema) {
        var exception = assertThrows(InvalidJsonSchemaException.class, () -> schemaDeserializer.deserialize(schema));
        assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
    }
}
