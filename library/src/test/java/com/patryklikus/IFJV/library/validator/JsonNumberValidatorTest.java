/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validator;

import com.patryklikus.IFJV.library.schema.model.NumberSchema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonNumberValidatorTest {
    private final JsonNumberValidator validator = new JsonNumberValidator();

    @ParameterizedTest
    @ValueSource(strings = {": 0.0,", ": 1.1 ,", ": 2.5}", ":  3 }"})
    @DisplayName("Should return true if json is valid")
    void validateTest(String input) throws JsonValidationException {
        NumberSchema schema = new NumberSchema(0.0, 3.0, null, null);

        int result = validator.validate(input, 1, schema);

        assertEquals(5, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {": 1", "-0.1", "1.1", "1;"})
    @DisplayName("Should throw exception if json is invalid")
    void invalidateTest(String input) {
        NumberSchema schema = new NumberSchema(0.0, 1.0, null, null);

        assertThrows(JsonValidationException.class, () -> validator.validate(input, 0, schema));
    }
}
