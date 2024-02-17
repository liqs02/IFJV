/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.models.*;
import com.patryklikus.ifjv.utils.CharUtils;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

public class JsonValidatorImpl implements JsonValidator {
    private static final Logger LOG = LoggerFactory.getLogger(JsonValidatorImpl.class);
    private final JsonBooleanValidator booleanValidator;
    private final JsonNumberValidator numberValidator;
    private final JsonStringValidator stringValidator;
    private final JsonObjectValidator objectValidator;
    private final JsonArrayValidator arrayValidator;

    public JsonValidatorImpl() {
        booleanValidator = new JsonBooleanValidatorImpl();
        numberValidator = new JsonNumberValidatorImpl();
        stringValidator = new JsonStringValidatorImpl();
        objectValidator = new JsonObjectValidatorImpl(this);
        arrayValidator = new JsonArrayValidatorImpl(this);
    }

    @Override
    @Nullable
    public String validate(char[] json, @NonNull JsonSchema schema) {
        try {
            int i = validate(json, 0, schema);
            for (; i < json.length; i++) {
                char character = json[i];
                if (!CharUtils.isWhiteSpace(character)) {
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

    int validate(char[] json, int i, JsonSchema schema) throws JsonValidationException {
        return switch (schema.getType()) {
            case ARRAY -> arrayValidator.validate(json, i, (ArraySchema) schema);
            case BOOLEAN -> booleanValidator.validate(json, i);
            case INTEGER -> numberValidator.validate(json, i, (IntegerSchema) schema);
            case NUMBER -> numberValidator.validate(json, i, (NumberSchema) schema);
            case OBJECT -> objectValidator.validate(json, i, (ObjectSchema) schema);
            case STRING -> stringValidator.validate(json, i, (StringSchema) schema);
        };
    }
}