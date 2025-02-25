/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.schema.model;

import com.patryklikus.IFJV.library.util.JsonElementType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode
@Getter
@ToString
public class ObjectSchema implements JsonSchema {
    private final boolean required;
    private final Set<String> dependentRequired;
    private final Map<String, JsonSchema> properties;

    public ObjectSchema(Boolean required, Set<String> dependentRequired, Map<String, JsonSchema> properties) {
        this.required = Objects.requireNonNullElse(required, false);
        this.dependentRequired = Objects.requireNonNullElseGet(dependentRequired, Collections::emptySet);
        if (properties == null)
            throw new IllegalArgumentException("properties must be defined");
        this.properties = properties;
    }

    public JsonSchema getProperty(@NonNull String key) {
        return properties.get(key);
    }

    @Override
    public JsonElementType getType() {
        return JsonElementType.OBJECT;
    }
}
