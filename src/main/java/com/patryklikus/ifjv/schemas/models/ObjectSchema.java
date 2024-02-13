/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patryklikus.ifjv.utils.JsonDataType;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
public class ObjectSchema implements JsonSchema {
    private final boolean required;
    private final Set<String> dependentRequired;
    @JsonIgnore
    @Setter
    private Map<String, JsonSchema> properties;

    public ObjectSchema(
            @JsonProperty("required") boolean required,
            @JsonProperty("dependentRequired") Set<String> dependentRequired
    ) {
        this.required = required;
        this.dependentRequired = Objects.requireNonNullElseGet(dependentRequired, Collections::emptySet);
    }

    public JsonSchema getProperty(@NonNull String key) {
        return properties.get(key);
    }

    @Override
    public JsonDataType getType() {
        return JsonDataType.OBJECT;
    }
}
