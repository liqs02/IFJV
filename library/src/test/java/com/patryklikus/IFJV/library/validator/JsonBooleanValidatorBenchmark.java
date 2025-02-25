/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validator;

import static org.junit.jupiter.api.Assertions.*;

import com.patryklikus.IFJV.library.schema.model.*;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JsonValidatorImpl")
class JsonBooleanValidatorBenchmark {
    private JsonValidator jsonValidator;

    @BeforeEach
    void setUp() {
        jsonValidator = new JsonValidator();
    }

    @Test
    @DisplayName("Should return true if json is valid")
    void validateTest() {
        String input = """
                [{"bool": true, "num": 1}, {"bool": true}]
                """;
        Map<String, JsonSchema> properties = Map.of(
                "bool", new BooleanSchema(),
                "num", new IntegerSchema(null, null, null, null)
        );
        ObjectSchema objectSchema = new ObjectSchema(false, Set.of("bool"), properties);
        ArraySchema arraySchema = new ArraySchema(null, null, objectSchema);

        String errorMessage = jsonValidator.validate(input, arraySchema);

        assertNull(errorMessage);
    }

    @Test
    @DisplayName("Should throw exception if json is invalid")
    void invalidateTest() {
        ObjectSchema objectSchema = new ObjectSchema(true, null, Collections.emptyMap());

        String errorMessage = jsonValidator.validate("{} INVALID", objectSchema);

        assertNotNull(errorMessage);
        assertEquals("Unexpected character at 3 index", errorMessage);
    }
}
