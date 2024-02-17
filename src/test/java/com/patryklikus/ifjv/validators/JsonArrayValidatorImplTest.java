/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import static com.patryklikus.ifjv.validators.JsonValidatorTestCases.INVALIDATE_JSON_TEST;
import static com.patryklikus.ifjv.validators.JsonValidatorTestCases.VALIDATE_JSON_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.patryklikus.ifjv.schemas.models.ArraySchema;
import com.patryklikus.ifjv.schemas.models.BooleanSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("JsonArrayValidatorImpl")
@ExtendWith(MockitoExtension.class)
class JsonArrayValidatorImplTest {
    @Mock
    private JsonValidatorImpl jsonValidator;
    private JsonArrayValidator jsonArrayValidator;

    @BeforeEach
    void setUp() {
        jsonArrayValidator = new JsonArrayValidatorImpl(jsonValidator);
    }

    @Test
    @DisplayName(VALIDATE_JSON_TEST)
    void validateTest() throws JsonValidationException {
        char[] input = ": [ true , false ] ,".toCharArray();
        BooleanSchema booleanSchema = new BooleanSchema();
        ArraySchema schema = new ArraySchema(2, 2, booleanSchema);
        when(jsonValidator.validate(input, 4, booleanSchema)).thenReturn(8);
        when(jsonValidator.validate(input, 11, booleanSchema)).thenReturn(16);

        int result = jsonArrayValidator.validate(input, 1, schema);

        assertEquals(18, result);
    }

    @Test
    @DisplayName(VALIDATE_JSON_TEST + " if array is empty")
    void validateEmptyArrayTest() throws JsonValidationException {
        char[] input = ": [ ] ,".toCharArray();
        BooleanSchema booleanSchema = new BooleanSchema();
        ArraySchema schema = new ArraySchema(0, null, booleanSchema);

        int result = jsonArrayValidator.validate(input, 1, schema);

        assertEquals(5, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            " [ ] ", " [ true ] ", " [ true , true , true ] "
    })
    @DisplayName(INVALIDATE_JSON_TEST)
    void invalidateTest(String input) {
        BooleanSchema booleanSchema = new BooleanSchema();
        ArraySchema schema = new ArraySchema(2, 2, booleanSchema);

        assertThrows(JsonValidationException.class, () -> jsonArrayValidator.validate(input.toCharArray(), 0, schema));
    }
}
