package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.models.IntegerSchema;
import com.patryklikus.ifjv.schemas.models.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.patryklikus.ifjv.validators.JsonValidatorTestCases.INVALIDATE_JSON_TEST;
import static com.patryklikus.ifjv.validators.JsonValidatorTestCases.VALIDATE_JSON_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("JsonNumberValidatorImpl")
class JsonNumberValidatorImplTest {
    private JsonNumberValidator jsonNumberValidator;

    @BeforeEach
    void setUp() {
        jsonNumberValidator = new JsonNumberValidatorImpl();
    }

    @Nested
    @DisplayName("Integer validation")
    class IntegerValidationTests {
        @ParameterizedTest
        @ValueSource(strings = {": 0,", ": 1 ,", ": 2}", ": 3 }"})
        @DisplayName(VALIDATE_JSON_TEST)
        void validateTest(String input) throws JsonValidationException {
            IntegerSchema schema = new IntegerSchema(0L, 3L, null, null);

            int result = jsonNumberValidator.validate(input.toCharArray(), 1, schema);

            assertEquals(3, result);
        }

        @ParameterizedTest
        @ValueSource(strings = {": 1", "1.1", "-1", "3", "1;"})
        @DisplayName(INVALIDATE_JSON_TEST)
        void invalidateTest(String input) {
            IntegerSchema schema = new IntegerSchema(0L, 2L, null, null);

            assertThrows(JsonValidationException.class, () -> jsonNumberValidator.validate(input.toCharArray(), 0, schema));
        }
    }

    @Nested
    @DisplayName("Number validation")
    class NumberValidationTests {
        @ParameterizedTest
        @ValueSource(strings = {": 0.0,", ": 1.1 ,", ": 2.5}", ":  3 }"})
        @DisplayName(VALIDATE_JSON_TEST)
        void validateTest(String input) throws JsonValidationException {
            NumberSchema schema = new NumberSchema(0.0, 3.0, null, null);

            int result = jsonNumberValidator.validate(input.toCharArray(), 1, schema);

            assertEquals(5, result);
        }

        @ParameterizedTest
        @ValueSource(strings = {": 1", "-0.1", "1.1", "1;"})
        @DisplayName(INVALIDATE_JSON_TEST)
        void invalidateTest(String input) {
            NumberSchema schema = new NumberSchema(0.0, 1.0, null, null);

            assertThrows(JsonValidationException.class, () -> jsonNumberValidator.validate(input.toCharArray(), 0, schema));
        }
    }
}
