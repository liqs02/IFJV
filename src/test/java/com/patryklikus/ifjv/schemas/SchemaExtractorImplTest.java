/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;


import static org.junit.jupiter.api.Assertions.assertNull;

import com.patryklikus.ifjv.validators.JsonValidator;
import com.patryklikus.ifjv.validators.JsonValidatorImpl;
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
                    min: -12
                    required: false
                  male:
                    type: array
                    required: true
                    items:
                        type: boolean
                """;

        Schema schema = schemaExtractor.extract(yaml);

        String json = """
                {
                  "age": -11,
                  "male": [true, false, true]
                }
                """;
        assertNull(jsonValidator.validate(json.toCharArray(), schema).get());
    }


    @Test
    void t2() {
        int[] num = {1};
        x(num);
        System.out.println(num[0]);
    }

    void x(int[] num) {
        num[0]++;
    }
}
