/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import static com.patryklikus.ifjv.validators.JsonValidatorTestCases.INVALIDATE_JSON_TEST;
import static com.patryklikus.ifjv.validators.JsonValidatorTestCases.VALIDATE_JSON_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.patryklikus.ifjv.schemas.models.BooleanSchema;
import com.patryklikus.ifjv.schemas.models.IntegerSchema;
import com.patryklikus.ifjv.schemas.models.JsonSchema;
import com.patryklikus.ifjv.schemas.models.ObjectSchema;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("JsonArrayValidatorImpl")
@ExtendWith(MockitoExtension.class)
class JsonObjectValidatorImplTest {
    @Mock
    private JsonValidatorImpl jsonValidator;
    private JsonObjectValidator jsonObjectValidator;

    @ParameterizedTest
    @ValueSource(strings = {"""
            : { "bool" : true, "num": 1 }
            """, """
            : { "bool" : true }
            """
    })
    @DisplayName(VALIDATE_JSON_TEST)
    void validateTest(String stringInput) throws JsonValidationException {
        jsonObjectValidator = new JsonObjectValidatorImpl(jsonValidator);
        char[] input = stringInput.toCharArray();
        int expected = stringInput.lastIndexOf('}') + 1;
        BooleanSchema booleanSchema = new BooleanSchema();
        IntegerSchema integerSchema = new IntegerSchema(null, null, null, null);
        Map<String, JsonSchema> properties = Map.of(
                "bool", booleanSchema,
                "num", integerSchema
        );
        ObjectSchema schema = new ObjectSchema(false, Set.of("bool"), properties);
        when(jsonValidator.validate(input, 12, booleanSchema)).thenReturn(17);
        if (stringInput.contains("num"))
            when(jsonValidator.validate(input, 25, integerSchema)).thenReturn(27);

        int result = jsonObjectValidator.validate(input, 1, schema);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"{}", " ", """
            {"num": x}
            """, """
            {"num": 1}
            """, """
            { "bool": true, "bool": false }
            """
    })
    @DisplayName(INVALIDATE_JSON_TEST)
    void invalidateTest(String stringInput) {
        jsonObjectValidator = new JsonObjectValidatorImpl(new JsonValidatorImpl());
        char[] input = stringInput.toCharArray();
        BooleanSchema booleanSchema = new BooleanSchema();
        IntegerSchema integerSchema = new IntegerSchema(null, null, null, null);
        Map<String, JsonSchema> properties = Map.of(
                "bool", booleanSchema,
                "num", integerSchema
        );
        ObjectSchema schema = new ObjectSchema(true, Set.of("bool"), properties);

        assertThrows(JsonValidationException.class, () -> jsonObjectValidator.validate(input, 0, schema));
    }
}
