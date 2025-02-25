/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.patryklikus.IFJV.library.schema.model.BooleanSchema;
import com.patryklikus.IFJV.library.schema.model.IntegerSchema;
import com.patryklikus.IFJV.library.schema.model.JsonSchema;
import com.patryklikus.IFJV.library.schema.model.ObjectSchema;
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
class JsonObjectValidatorTest {
    @Mock
    private JsonValidator jsonValidator;
    private JsonObjectValidator jsonObjectValidator;

    @ParameterizedTest
    @ValueSource(strings = {"""
            : { "bool" : true, "num": 1 }
            """, """
            : { "bool" : true }
            """
    })
    @DisplayName("Should return true if json is valid")
    void validateTest(String input) throws JsonValidationException {
        jsonObjectValidator = new JsonObjectValidator(jsonValidator);
        int expected = input.lastIndexOf('}') + 1;
        BooleanSchema booleanSchema = new BooleanSchema();
        IntegerSchema integerSchema = new IntegerSchema(null, null, null, null);
        Map<String, JsonSchema> properties = Map.of(
                "bool", booleanSchema,
                "num", integerSchema
        );
        ObjectSchema schema = new ObjectSchema(false, Set.of("bool"), properties);
        when(jsonValidator.validate(input, 12, booleanSchema)).thenReturn(17);
        if (input.contains("num"))
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
    @DisplayName("Should throw exception if json is invalid")
    void invalidateTest(String input) {
        jsonObjectValidator = new JsonObjectValidator(new JsonValidator());
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
