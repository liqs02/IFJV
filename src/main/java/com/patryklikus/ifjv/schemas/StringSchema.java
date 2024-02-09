package com.patryklikus.ifjv.schemas;

public interface StringSchema {
    boolean isRequired();
    Integer getMinLength();
    Integer getMaxLength();
}
