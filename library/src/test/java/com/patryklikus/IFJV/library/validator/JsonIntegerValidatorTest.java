/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validator;

import com.patryklikus.IFJV.library.schema.model.IntegerSchema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonIntegerValidatorTest {
    private final JsonIntegerValidator validator = new JsonIntegerValidator();

    @ParameterizedTest
    @ValueSource(strings = {": 0,", ": 1 ,", ": 2}", ": 3 }"})
    @DisplayName("Should return true if json is valid")
    void validateTest(String input) throws JsonValidationException {
        IntegerSchema schema = new IntegerSchema(0L, 3L, null, null);

        int result = validator.validate(input, 1, schema);

        assertEquals(3, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {": 1", "1.1", "-1", "3", "1;"})
    @DisplayName("Should throw exception if json is invalid")
    void invalidateTest(String input) {
        IntegerSchema schema = new IntegerSchema(0L, 2L, null, null);

        assertThrows(JsonValidationException.class, () -> validator.validate(input, 0, schema));
    }
}
