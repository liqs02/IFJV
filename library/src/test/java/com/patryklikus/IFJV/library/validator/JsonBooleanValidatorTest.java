/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("JsonBooleanValidatorImpl")
public class JsonBooleanValidatorTest {
    private JsonBooleanValidator jsonBooleanValidator;

    @BeforeEach
    void setUp() {
        jsonBooleanValidator = new JsonBooleanValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {": true  ", ": false  "})
    @DisplayName(JsonValidatorTestCases.VALIDATE_JSON_TEST)
    void validateTest(String input) throws JsonValidationException {
        int expected = input.indexOf('e') + 1;

        int result = jsonBooleanValidator.validate(input, 1);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            ":true", ":false", "t rue", "f alse", "",
            "True", "tRue", "trUe", "truE",
            "False", "fAlse", "faLse", "falSe", "falsE"
    })
    @DisplayName(JsonValidatorTestCases.INVALIDATE_JSON_TEST)
    void invalidateTest(String input) {
        assertThrows(JsonValidationException.class, () -> jsonBooleanValidator.validate(input, 0));
    }
}
