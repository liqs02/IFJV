package com.patryklikus.ifjv.schemas;

import lombok.NonNull;

public interface ObjectSchema {
    boolean isRequired();

    int getPropertiesCount();

    Schema getPropertySchema(@NonNull String key);
}
