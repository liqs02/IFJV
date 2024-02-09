package com.patryklikus.ifjv.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ArraySchema {
    boolean isRequired();
    Integer getMinSize();
    Integer getMaxSize();
    Schema getItems();
}
