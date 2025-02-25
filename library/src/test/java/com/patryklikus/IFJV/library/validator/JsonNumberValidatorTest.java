/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.patryklikus.IFJV.library.schema.model.IntegerSchema;
import com.patryklikus.IFJV.library.schema.model.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("JsonNumberValidatorImpl")
class JsonNumberValidatorTest {
    private JsonNumberValidator jsonNumberValidator;

    @BeforeEach
    void setUp() {
        jsonNumberValidator = new JsonNumberValidator();
    }

    @Nested
    @DisplayName("Integer validation")
    class IntegerValidationTests {
        @ParameterizedTest
        @ValueSource(strings = {": 0,", ": 1 ,", ": 2}", ": 3 }"})
        @DisplayName("Should return true if json is valid")
        void validateTest(String input) throws JsonValidationException {
            IntegerSchema schema = new IntegerSchema(0L, 3L, null, null);

            int result = jsonNumberValidator.validate(input, 1, schema);

            assertEquals(3, result);
        }

        @ParameterizedTest
        @ValueSource(strings = {": 1", "1.1", "-1", "3", "1;"})
        @DisplayName("Should throw exception if json is invalid")
        void invalidateTest(String input) {
            IntegerSchema schema = new IntegerSchema(0L, 2L, null, null);

            assertThrows(JsonValidationException.class, () -> jsonNumberValidator.validate(input, 0, schema));
        }
    }

    @Nested
    @DisplayName("Number validation")
    class NumberValidationTests {
        @ParameterizedTest
        @ValueSource(strings = {": 0.0,", ": 1.1 ,", ": 2.5}", ":  3 }"})
        @DisplayName("Should return true if json is valid")
        void validateTest(String input) throws JsonValidationException {
            NumberSchema schema = new NumberSchema(0.0, 3.0, null, null);

            int result = jsonNumberValidator.validate(input, 1, schema);

            assertEquals(5, result);
        }

        @ParameterizedTest
        @ValueSource(strings = {": 1", "-0.1", "1.1", "1;"})
        @DisplayName("Should throw exception if json is invalid")
        void invalidateTest(String input) {
            NumberSchema schema = new NumberSchema(0.0, 1.0, null, null);

            assertThrows(JsonValidationException.class, () -> jsonNumberValidator.validate(input, 0, schema));
        }
    }
}
