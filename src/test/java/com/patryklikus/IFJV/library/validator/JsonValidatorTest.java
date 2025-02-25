/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validator;

import com.patryklikus.IFJV.library.schema.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JsonValidatorTest {
    private JsonValidator validator;

    @BeforeEach
    void setUp() {
        validator = new JsonValidator();
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

        String errorMessage = validator.validate(input, arraySchema);

        assertNull(errorMessage);
    }

    @Test
    @DisplayName("Should throw exception if json is invalid")
    void invalidateTest() {
        ObjectSchema objectSchema = new ObjectSchema(true, null, Collections.emptyMap());

        String errorMessage = validator.validate("{} INVALID", objectSchema);

        assertNotNull(errorMessage);
        assertEquals("Unexpected character at 3 index", errorMessage);
    }
}
