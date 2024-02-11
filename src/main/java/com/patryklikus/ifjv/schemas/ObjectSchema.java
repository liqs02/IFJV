/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;

import java.util.Map;
import lombok.NonNull;

public interface ObjectSchema {
    boolean isRequired();

    int getPropertiesCount();

    Map<String, Schema> getProperties();

    Schema getProperty(@NonNull String key);

    int getRequiredPropertiesCount();
}
