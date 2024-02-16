/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patryklikus.ifjv.utils.JsonDataType;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class ObjectSchema implements JsonSchema {
    private final boolean required;
    private final Set<String> dependentRequired;
    @JsonIgnore
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

    public void setProperties(Map<String, JsonSchema> properties) {
        if (properties == null)
            throw new IllegalArgumentException("properties must be defined");
        this.properties = properties;
    }
}
