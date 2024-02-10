/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;


import com.patryklikus.ifjv.validators.JsonValidator;
import com.patryklikus.ifjv.validators.JsonValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

        String json = """
                {
                  "age": -11
                }
                """;

        assertTrue(jsonValidator.validate(json.toCharArray(), schema));
    }

    @Test
    void t2() {
        System.out.println((int) '-');
        System.out.println((int) '0');
        System.out.println((int) '9');
    }
}
