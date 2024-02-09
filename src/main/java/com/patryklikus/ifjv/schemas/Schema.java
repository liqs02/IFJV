/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patryklikus.ifjv.JsonDataType;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Schema {
    @JsonProperty(required = true)
    private JsonDataType type;
    @JsonProperty
    private boolean required;
    @JsonProperty
    private Integer min;
    @JsonProperty
    private Integer max;
    @JsonProperty
    private Map<String, Schema> properties;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schema schema = (Schema) o;
        return required == schema.required && type == schema.type && Objects.equals(min, schema.min) && Objects.equals(max, schema.max) && Objects.equals(properties, schema.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, required, min, max, properties);
    }

    @Override
    public String toString() {
        return "Schema{" +
                "type=" + type +
                ", required=" + required +
                ", min=" + min +
                ", max=" + max +
                ", properties=" + properties +
                '}';
    }
}
