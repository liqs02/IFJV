/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;


import static org.junit.jupiter.api.Assertions.assertThrows;

import com.patryklikus.ifjv.schemas.models.JsonSchema;
import com.patryklikus.ifjv.validators.JsonValidator;
import com.patryklikus.ifjv.validators.JsonValidatorImpl;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
                    minimum: -12
                    required: false
                  male:
                    type: boolean
                    required: true
                """;

        JsonSchema schema = schemaExtractor.extract(yaml);

        String json = "{\"male\": true}";
        assertThrows(NoSuchElementException.class, () -> jsonValidator.validate(json.toCharArray(), schema).get());
    }
}
