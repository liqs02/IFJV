/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.patryklikus.IFJV.library.schemas.models.ArraySchema;
import com.patryklikus.IFJV.library.schemas.models.BooleanSchema;
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
    @DisplayName(JsonValidatorTestCases.VALIDATE_JSON_TEST)
    void validateTest() throws JsonValidationException {
        String input = ": [ true , false ] ,";
        BooleanSchema booleanSchema = new BooleanSchema();
        ArraySchema schema = new ArraySchema(2, 2, booleanSchema);
        when(jsonValidator.validate(input, 4, booleanSchema)).thenReturn(8);
        when(jsonValidator.validate(input, 11, booleanSchema)).thenReturn(16);

        int result = jsonArrayValidator.validate(input, 1, schema);

        assertEquals(18, result);
    }

    @Test
    @DisplayName(JsonValidatorTestCases.VALIDATE_JSON_TEST + " if array is empty")
    void validateEmptyArrayTest() throws JsonValidationException {
        String input = ": [ ] ,";
        BooleanSchema booleanSchema = new BooleanSchema();
        ArraySchema schema = new ArraySchema(0, null, booleanSchema);

        int result = jsonArrayValidator.validate(input, 1, schema);

        assertEquals(5, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            " [ ] ", " [ true ] ", " [ true , true , true ] "
    })
    @DisplayName(JsonValidatorTestCases.INVALIDATE_JSON_TEST)
    void invalidateTest(String input) {
        BooleanSchema booleanSchema = new BooleanSchema();
        ArraySchema schema = new ArraySchema(2, 2, booleanSchema);

        assertThrows(JsonValidationException.class, () -> jsonArrayValidator.validate(input, 0, schema));
    }
}
