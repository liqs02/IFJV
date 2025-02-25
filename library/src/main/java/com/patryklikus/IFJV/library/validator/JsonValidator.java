/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validator;

import com.patryklikus.IFJV.library.schema.model.*;
import com.patryklikus.IFJV.library.util.CharUtils;
import javax.annotation.Nullable;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonValidator {
    private static final Logger LOG = LoggerFactory.getLogger(JsonValidator.class);
    private final JsonBooleanValidator booleanValidator;
    private final JsonIntegerValidator integerValidator;
    private final JsonNumberValidator numberValidator;
    private final JsonStringValidator stringValidator;
    private final JsonObjectValidator objectValidator;
    private final JsonArrayValidator arrayValidator;

    public JsonValidator() {
        booleanValidator = new JsonBooleanValidator();
        integerValidator = new JsonIntegerValidator();
        numberValidator = new JsonNumberValidator();
        stringValidator = new JsonStringValidator();
        objectValidator = new JsonObjectValidator(this);
        arrayValidator = new JsonArrayValidator(this);
    }

    /**
     * @return error message, if null then JSON is valid.
     */
    @Nullable
    public String validate(String json, @NonNull JsonSchema schema) {
        try {
            int i = validate(json, 0, schema);
            for (; i < json.length(); i++) {
                if (!CharUtils.isWhiteSpace(json.charAt(i))) {
                    return String.format("Unexpected character at %d index", i);
                }
            }
        } catch (JsonValidationException e) {
            return e.getMessage();
        } catch (RuntimeException e) {
            LOG.warn("Unexpected validation error", e);
            return "Invalid JSON";
        }
        return null;
    }

    int validate(String json, int i, JsonSchema schema) throws JsonValidationException {
        return switch (schema.getType()) {
            case ARRAY -> arrayValidator.validate(json, i, (ArraySchema) schema);
            case BOOLEAN -> booleanValidator.validate(json, i, (BooleanSchema) schema);
            case INTEGER -> integerValidator.validate(json, i, (IntegerSchema) schema);
            case NUMBER -> numberValidator.validate(json, i, (NumberSchema) schema);
            case OBJECT -> objectValidator.validate(json, i, (ObjectSchema) schema);
            case STRING -> stringValidator.validate(json, i, (StringSchema) schema);
        };
    }
}