/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.patryklikus.ifjv.utils.JsonDataType;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface JsonSchema {
    JsonDataType getType();
}
