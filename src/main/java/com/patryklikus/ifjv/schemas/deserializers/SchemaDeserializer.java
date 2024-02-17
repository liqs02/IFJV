/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas.deserializers;

import com.patryklikus.ifjv.schemas.models.JsonSchema;
import lombok.NonNull;

public interface SchemaDeserializer {
    JsonSchema deserialize(@NonNull String yamlSchema) throws InvalidJsonSchemaException;
}
