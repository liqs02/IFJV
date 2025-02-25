/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schema.deserializer;

import com.patryklikus.IFJV.library.schema.model.JsonSchema;
import lombok.NonNull;

public interface SchemaDeserializer {
    JsonSchema deserialize(@NonNull String yamlSchema) throws InvalidJsonSchemaException;
}
