/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.CharUtils;
import com.patryklikus.ifjv.schemas.Schema;
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
    public Optional<String> validate(char[] json, Schema schema) {
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
        } catch (ValidationException e) {
            return Optional.of(e.getMessage());
        } catch (RuntimeException e) {
            LOG.warn("Unexpected validation error", e);
            return Optional.of("Invalid JSON");
        }
        return Optional.empty();
    }

    int validate(char[] json, int i, Schema schema) throws ValidationException {
        return switch (schema.getType()) {
            case BOOLEAN -> booleanValidator.validateBoolean(json, i);
            case INTEGER -> numberValidator.validateInteger(json, i, schema);
            case DOUBLE -> numberValidator.validateDouble(json, i, schema);
            case STRING -> stringValidator.validateString(json, i, schema);
            case OBJECT -> objectValidator.validateObject(json, i, schema);
            case ARRAY -> arrayValidator.validateArray(json, i, schema);
        };
    }
}