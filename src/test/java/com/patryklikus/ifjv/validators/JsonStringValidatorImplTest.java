package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.models.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.patryklikus.ifjv.validators.JsonValidatorTestCases.INVALIDATE_JSON_TEST;
import static com.patryklikus.ifjv.validators.JsonValidatorTestCases.VALIDATE_JSON_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("JsonNumberValidatorImpl")
class JsonStringValidatorImplTest {
    private JsonStringValidator jsonStringValidator;

    @BeforeEach
    void setUp() {
        jsonStringValidator = new JsonStringValidatorImpl();
    }

    @Test
    @DisplayName(VALIDATE_JSON_TEST + " if string has maximum length")
    void validateMaxLengthTest() throws JsonValidationException {
        String input = """
                : "val\\""}
                """;
        StringSchema schema = new StringSchema(1, 4);

        int result = jsonStringValidator.validate(input.toCharArray(), 1, schema);

        assertEquals(9, result);
    }

    @Test
    @DisplayName(VALIDATE_JSON_TEST + " if string has minimum length")
    void validateMinLengthTest() throws JsonValidationException {
        String input = """
                : "\\n",
                """;
        StringSchema schema = new StringSchema(1, 4);

        int result = jsonStringValidator.validate(input.toCharArray(), 1, schema);

        assertEquals(6, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            " \"x\" ", " \"xxxx\" ", " xxx "
    })
    @DisplayName(INVALIDATE_JSON_TEST)
    void invalidateTest(String input) {
        StringSchema schema = new StringSchema(2, 3);

        assertThrows(JsonValidationException.class, () -> jsonStringValidator.validate(input.toCharArray(), 0, schema));
    }
}
