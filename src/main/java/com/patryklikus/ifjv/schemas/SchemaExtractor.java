/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;

import com.patryklikus.ifjv.schemas.models.JsonSchema;
import lombok.NonNull;

public interface SchemaExtractor {
    JsonSchema extract(@NonNull String yamlSchema) throws InvalidJsonSchemaException;
}
