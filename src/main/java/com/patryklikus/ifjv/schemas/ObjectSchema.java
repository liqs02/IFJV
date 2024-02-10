package com.patryklikus.ifjv.schemas;

import lombok.NonNull;

import java.util.Map;

public interface ObjectSchema {
    boolean isRequired();

    int getPropertiesCount();

    Map<String, Schema> getProperties();

    Schema getProperty(@NonNull String key);

    int getRequiredPropertiesCount();
}
