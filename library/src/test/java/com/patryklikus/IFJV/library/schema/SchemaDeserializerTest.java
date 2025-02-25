/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schema;

import com.patryklikus.IFJV.library.schema.deserializer.InvalidJsonSchemaException;
import com.patryklikus.IFJV.library.schema.deserializer.SchemaDeserializer;
import com.patryklikus.IFJV.library.schema.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SchemaDeserializerTest {
    private final SchemaDeserializer schemaDeserializer = new SchemaDeserializer();

    @Nested
    @DisplayName("ArraySchema")
    class ArraySchemaTests {
        @ParameterizedTest
        @ValueSource(strings = {
                "type: array\nitems:\n type: boolean",
                "type: array\nitems: boolean"
        })
        @DisplayName("Should deserialize default schema")
        void defaultSchemaTest(String input) {
            ArraySchema expected = new ArraySchema(0, Integer.MAX_VALUE, new BooleanSchema());

            JsonSchema schema = schemaDeserializer.deserialize(input);

            assertEquals(expected, schema);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "type: array\nminItems: 1\nmaxItems: 1\nitems:\n type: boolean",
                "type: array\nminItems: 1\nmaxItems: 1\nitems: boolean"
        })
        @DisplayName("Should deserialize schema with optional properties")
        void schemaTest(String input) {
            ArraySchema expected = new ArraySchema(1, 1, new BooleanSchema());

            JsonSchema schema = schemaDeserializer.deserialize(input);

            assertEquals(expected, schema);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "type: array\nitems: boolean\nminItems: -1",
                "type: array\nitems: boolean\nmaxItems: -1",
                "type: array\nitems: boolean\nminItems: 2\nmaxItems: 1",
                "type: array\nitems: 0",
                "type: array\nminItems: x",
                "type: array\nmaxItems: x",
                "type: array"
        })
        @DisplayName("Should throw exception")
        void invalidSchemaTest(String schema) {
            var exception = assertThrows(InvalidJsonSchemaException.class, () -> schemaDeserializer.deserialize(schema));

            assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
        }
    }

    @Nested
    @DisplayName("BooleanSchema")
    class BooleanSchemaTests {
        @ParameterizedTest
        @ValueSource(strings = {"type: boolean", "boolean"})
        @DisplayName("Should deserialize default schema")
        void schemaTest(String input) {
            JsonSchema schema = schemaDeserializer.deserialize(input);

            assertNotNull(schema);
        }
    }

    @Nested
    @DisplayName("IntegerSchema")
    class IntegerSchemaTests {
        @ParameterizedTest
        @ValueSource(strings = {"type: integer", "integer"})
        @DisplayName("Should deserialize default schema")
        void defaultSchemaTest(String input) {
            var expected = new IntegerSchema(Long.MIN_VALUE, Long.MAX_VALUE, null, null);

            JsonSchema schema = schemaDeserializer.deserialize(input);

            assertEquals(expected, schema);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "type: integer\nminimum: -1\nmaximum: 2",
                "type: integer\nexclusiveMinimum: -2\nexclusiveMaximum: 3"
        })
        @DisplayName("Should deserialize schema with optional properties")
        void schemaTest(String input) {
            var expected = new IntegerSchema(-1L, 2L, null, null);

            JsonSchema schema = schemaDeserializer.deserialize(input);

            assertEquals(expected, schema);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "type: integer\nminimum: 0\nexclusiveMinimum: 0",
                "type: integer\nmaximum: 0\nexclusiveMaximum: 0",
                "type: integer\nminimum: 0.1\nmaximum: 0",
                "type: integer\nexclusiveMinimum: 0.1\nmaximum: 0",
                "type: integer\nminimum: 0.1\nexclusiveMaximum: 0"
        })
        @DisplayName("Should throw exception")
        void invalidSchemaTest(String schema) {
            var exception = assertThrows(InvalidJsonSchemaException.class, () -> schemaDeserializer.deserialize(schema));

            assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
        }
    }

    @Nested
    @DisplayName("NumberSchema")
    class NumberSchemaTests {
        @ParameterizedTest
        @ValueSource(strings = {"type: number", "number"})
        @DisplayName("Should deserialize default schema")
        void defaultSchemaTest(String input) {
            var expected = new NumberSchema(Double.MIN_VALUE, Double.MAX_VALUE, null, null);

            JsonSchema schema = schemaDeserializer.deserialize(input);

            assertEquals(expected, schema);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "type: number\nminimum: -0.5\nmaximum: 1.5",
                "type: number\nexclusiveMinimum: -0.5000000000000001\nexclusiveMaximum: 1.5000000000000002",
                "type: number\nexclusiveMinimum: -0.5000000000000001\nmaximum: 1.5",
                "type: number\nminimum: -0.5\nexclusiveMaximum: 1.5000000000000002",
        })
        @DisplayName("Should deserialize schema with optional properties")
        void SchemaTest(String input) {
            var expected = new NumberSchema(-0.5, 1.5, null, null);

            JsonSchema schema = schemaDeserializer.deserialize(input);

            assertEquals(expected, schema);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "type: number\nminimum: 0\nexclusiveMinimum: 0",
                "type: number\nmaximum: 0\nexclusiveMaximum: 0",
                "type: number\nminimum: 0.1\nmaximum: 0",
                "type: number\nexclusiveMinimum: 0.1\nmaximum: 0",
                "type: number\nminimum: 0.1\nexclusiveMaximum: 0"
        })
        @DisplayName("Should throw exception")
        void invalidSchemaTest(String schema) {
            var exception = assertThrows(InvalidJsonSchemaException.class, () -> schemaDeserializer.deserialize(schema));

            assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
        }
    }

    @Nested
    @DisplayName("ObjectSchema")
    class ObjectSchemaTests {
        @ParameterizedTest
        @ValueSource(strings = {"""
                type: object
                properties:
                    bool: boolean
                """, """
                type: object
                properties:
                    bool:
                        type: boolean
                """
        })
        @DisplayName("Should deserialize default schema")
        void defaultSchemaTest(String input) {
            Map<String, JsonSchema> arrayProperties = new HashMap<>();
            arrayProperties.put("bool", new BooleanSchema());
            ObjectSchema expected = new ObjectSchema(false, Collections.emptySet(), arrayProperties);

            JsonSchema schema = schemaDeserializer.deserialize(input);

            assertEquals(expected, schema);
        }

        @ParameterizedTest
        @ValueSource(strings = {"""
                type: object
                required: true
                dependentRequired:
                    - bool
                    - num
                properties:
                    bool: boolean
                    text: string
                    num:
                        type: integer
                        minimum: 1
                        maximum: 2
                """, """
                type: object
                required: true
                dependentRequired: bool, num
                properties:
                    bool: boolean
                    text: string
                    num:
                        type: integer
                        minimum: 1
                        maximum: 2
                """
        })
        @DisplayName("Should deserialize schema with optional properties")
        void schemaTest(String input) {
            Map<String, JsonSchema> expectedProperties = new HashMap<>();
            expectedProperties.put("bool", new BooleanSchema());
            expectedProperties.put("text", new StringSchema(null, null));
            expectedProperties.put("num", new IntegerSchema(1L, 2L, null, null));
            ObjectSchema expected = new ObjectSchema(true, Set.of("bool", "num"), expectedProperties);
            JsonSchema schema = schemaDeserializer.deserialize(input);

            assertEquals(expected, schema);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "type: object",
                "type: object\nrequired: x",
                "type: object\nproperties: 0",
                "type: object\nproperties: x",
        })
        @DisplayName("Should throw exception")
        void invalidSchemaTest(String schema) {
            var exception = assertThrows(InvalidJsonSchemaException.class, () -> schemaDeserializer.deserialize(schema));

            assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
        }
    }

    @Nested
    @DisplayName("StringSchema")
    class StringSchemaTests {
        @ParameterizedTest
        @ValueSource(strings = {"type: string", "string"})
        @DisplayName("Should deserialize default schema")
        void defaultSchemaTest(String input) {
            var expected = new StringSchema(0, Integer.MAX_VALUE);

            JsonSchema schema = schemaDeserializer.deserialize(input);

            assertEquals(expected, schema);
        }

        @Test
        @DisplayName("Should deserialize schema with optional properties")
        void schemaTest() {
            var expected = new StringSchema(1, 3);

            JsonSchema schema = schemaDeserializer.deserialize("type: string\nminLength: 1\nmaxLength: 3");

            assertEquals(expected, schema);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "type: string\nminLength: -1",
                "type: string\nmaxLength: -1",
        })
        @DisplayName("Should throw exception")
        void invalidSchemaTest(String schema) {
            var exception = assertThrows(InvalidJsonSchemaException.class, () -> schemaDeserializer.deserialize(schema));

            assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
        }
    }
}
