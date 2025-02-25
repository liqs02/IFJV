/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validator;

import com.patryklikus.IFJV.library.schema.model.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonStringValidatorTest {
    private JsonStringValidator validator;

    @BeforeEach
    void setUp() {
        validator = new JsonStringValidator();
    }

    @Test
    @DisplayName("Should return true if string has maximum length")
    void validateMaxLengthTest() throws JsonValidationException {
        String input = """
                : "val\\""}
                """;
        StringSchema schema = new StringSchema(1, 4);

        int result = validator.validate(input, 1, schema);

        assertEquals(9, result);
    }

    @Test
    @DisplayName("Should return true if string has minimum length")
    void validateMinLengthTest() throws JsonValidationException {
        String input = """
                : "\\n",
                """;
        StringSchema schema = new StringSchema(1, 4);

        int result = validator.validate(input, 1, schema);

        assertEquals(6, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            " \"x\" ", " \"xxxx\" ", " xxx "
    })
    @DisplayName("Should throw exception if json is invalid")
    void invalidateTest(String input) {
        StringSchema schema = new StringSchema(2, 3);

        assertThrows(JsonValidationException.class, () -> validator.validate(input, 0, schema));
    }
}
