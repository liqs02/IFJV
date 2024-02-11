/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;


import com.patryklikus.ifjv.validators.JsonValidator;
import com.patryklikus.ifjv.validators.JsonValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SchemaExtractorImplTest {
    private SchemaExtractor schemaExtractor;
    private JsonValidator jsonValidator;

    @BeforeEach
    void setUp() {
        schemaExtractor = new SchemaExtractorImpl();
        jsonValidator = new JsonValidatorImpl();
    }

    @Test
    void extractTest() {
        String yaml = """
                type: object
                properties:
                  age:
                    type: integer
                    min: -12
                    required: false
                  male:
                    type: boolean
                    required: true
                """;

        Schema schema = schemaExtractor.extract(yaml);

        String json = "{\"male\": true}";
        assertThrows(NoSuchElementException.class, () -> jsonValidator.validate(json.toCharArray(), schema).get());
    }
}
