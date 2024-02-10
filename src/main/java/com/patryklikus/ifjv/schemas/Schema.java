/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patryklikus.ifjv.JsonDataType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class Schema implements NumberSchema, DoubleSchema, StringSchema, BooleanSchema, ArraySchema, ObjectSchema {
    @JsonProperty(required = true)
    private JsonDataType type;
    @JsonProperty
    private boolean required;
    @JsonProperty
    private Double min;
    @JsonProperty
    private Double max;
    @JsonProperty
    private Integer minLength;
    @JsonProperty
    private Integer maxLength;
    @JsonProperty
    private Integer minSize;
    @JsonProperty
    private Integer maxSize;
    @JsonProperty
    private Schema items;
    @JsonProperty
    private Map<String, Schema> properties;
    @Setter
    private int requiredPropertiesCount;

    @Override
    public int getPropertiesCount() {
        return properties.size();
    }

    public Schema getProperty(String key) {
        if (properties == null) {
            return null;
        }
        return properties.get(key);
    }

}
