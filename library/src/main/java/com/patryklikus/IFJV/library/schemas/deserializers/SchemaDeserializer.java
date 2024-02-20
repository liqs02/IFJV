/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schemas.deserializers;

import com.patryklikus.IFJV.library.schemas.models.JsonSchema;
import lombok.NonNull;

public interface SchemaDeserializer {
    JsonSchema deserialize(@NonNull String yamlSchema) throws InvalidJsonSchemaException;
}
