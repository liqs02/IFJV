/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.models.*;
import com.patryklikus.ifjv.utils.CharUtils;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonValidatorImpl implements JsonValidator {
    private static final Logger LOG = LoggerFactory.getLogger(JsonValidatorImpl.class);
    private final JsonBooleanValidator booleanValidator;
    private final JsonNumberValidator numberValidator;
    private final JsonStringValidatorImpl stringValidator;
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
    public Optional<String> validate(char[] json, JsonSchema schema) {
        // todo required doesn't work if property is not inside object
        try {
            int i = validate(json, 0, schema);
            for (; i < json.length; i++) {
                char character = json[i];
                if (!CharUtils.isWhiteSpace(character)) {
                    String message = String.format("Unexpected character at %d index", i);
                    return Optional.of(message);
                }
            }
        } catch (JsonValidationException e) {
            return Optional.of(e.getMessage());
        } catch (RuntimeException e) {
            LOG.warn("Unexpected validation error", e);
            return Optional.of("Invalid JSON");
        }
        return Optional.empty();
    }

    int validate(char[] json, int i, JsonSchema schema) throws JsonValidationException {
        return switch (schema.getType()) {
            case BOOLEAN -> booleanValidator.validate(json, i);
            case INTEGER -> numberValidator.validate(json, i, (IntegerSchema) schema);
            case NUMBER -> numberValidator.validate(json, i, (NumberSchema) schema);
            case STRING -> stringValidator.validate(json, i, (StringSchema) schema);
            case OBJECT -> objectValidator.validate(json, i, (ObjectSchema) schema);
            case ARRAY -> arrayValidator.validate(json, i, (ArraySchema) schema);
        };
    }
}