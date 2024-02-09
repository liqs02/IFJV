/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;

import lombok.NonNull;

public interface SchemaExtractor {
    Schema extract(@NonNull String yamlSchema);
}
