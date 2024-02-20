/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.validators;

import com.patryklikus.IFJV.library.schemas.models.JsonSchema;
import com.patryklikus.IFJV.library.schemas.models.ObjectSchema;
import com.patryklikus.IFJV.library.utils.CharUtils;
import gnu.trove.list.linked.TCharLinkedList;
import java.util.HashSet;
import java.util.Set;

class JsonObjectValidatorImpl implements JsonObjectValidator {
    private final JsonValidatorImpl jsonValidator;

    public JsonObjectValidatorImpl(JsonValidatorImpl jsonValidator) {
        this.jsonValidator = jsonValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int validate(String json, int i, ObjectSchema schema) throws JsonValidationException {
        // 1 step
        while (i < json.length()) {
            char character = json.charAt(i++);
            if (!CharUtils.isWhiteSpace(character)) {
                if (character == '{') {
                    break;
                } else {
                    throw new JsonValidationException("Object doesn't begin with {", --i);
                }
            }
        }
        // step 2
        int[] indexPointer = new int[1];
        TCharLinkedList extractedString = null;
        int processedRequiredProps = 0;
        for (; i < json.length(); i++) {
            char character = json.charAt(i);
            if (!CharUtils.isWhiteSpace(character)) {
                if (character == '}') {
                    if (schema.getDependentRequired().isEmpty())
                        return ++i;
                    else
                        throw new JsonValidationException("Object is empty and doesn't contain required fields", i);
                } else {
                    break;
                }
            }
        }
        Set<String> processedFields = new HashSet<>(schema.getProperties().size() + 1, 1);
        for (; i < json.length(); i++) {
            // step 3
            while (i < json.length()) {
                char character = json.charAt(i++);
                if (!CharUtils.isWhiteSpace(character)) {
                    if (character == '"') {
                        indexPointer[0] = i - 1;
                        extractedString = CharUtils.extractString(json, indexPointer);
                        i = indexPointer[0];
                        break;
                    } else {
                        throw new JsonValidationException("Invalid key string", --i);
                    }
                }
            }
            if (extractedString == null)
                throw new JsonValidationException("Object doesn't end properly", --i);
            // step 4
            var key = new String(extractedString.toArray());
            if (processedFields.contains(key)) {
                throw new JsonValidationException(key + " field is duplicated in object", --i);
            }
            processedFields.add(key);
            // step 5
            while (i < json.length()) {
                char character = json.charAt(i++);
                if (!CharUtils.isWhiteSpace(character)) {
                    if (character == ':') {
                        break;
                    } else {
                        throw new JsonValidationException("Unexpected character", --i);
                    }
                }
            }
            // step 6
            JsonSchema propertySchema = schema.getProperty(key);
            if (propertySchema == null) {
                throw new JsonValidationException("Object has a prohibited field", --i);
            }
            i = jsonValidator.validate(json, i, propertySchema);
            if (schema.getDependentRequired().contains(key))
                processedRequiredProps++;
            // step 7
            while (i < json.length()) {
                char character = json.charAt(i++);
                if (!CharUtils.isWhiteSpace(character)) {
                    if (character == ',')
                        break;
                    if (character == '}') {
                        if (schema.getDependentRequired().size() != processedRequiredProps) {
                            throw new JsonValidationException("All required fields have to be declared in object", --i);
                        }
                        return i;
                    }
                }
            }
        }
        if (schema.isRequired())
            throw new JsonValidationException("Required object isn't defined", i);
        else
            return i;
    }
}