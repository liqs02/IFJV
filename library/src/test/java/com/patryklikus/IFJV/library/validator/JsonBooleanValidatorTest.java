/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validator;

import com.patryklikus.IFJV.library.schema.model.BooleanSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonBooleanValidatorTest {
    private JsonBooleanValidator jsonBooleanValidator;

    @BeforeEach
    void setUp() {
        jsonBooleanValidator = new JsonBooleanValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {": true  ", ": false  "})
    @DisplayName("Should return true if json is valid")
    void validateTest(String input) throws JsonValidationException {
        int expected = input.indexOf('e') + 1;

        int result = jsonBooleanValidator.validate(input, 1, new BooleanSchema());

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            ":true", ":false", "t rue", "f alse", "",
            "True", "tRue", "trUe", "truE",
            "False", "fAlse", "faLse", "falSe", "falsE"
    })
    @DisplayName("Should throw exception if json is invalid")
    void invalidateTest(String input) {
        assertThrows(JsonValidationException.class, () -> jsonBooleanValidator.validate(input, 0, new BooleanSchema()));
    }
}
