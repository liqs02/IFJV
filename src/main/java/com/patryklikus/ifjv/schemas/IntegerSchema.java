package com.patryklikus.ifjv.schemas;

public interface IntegerSchema {
    boolean isRequired();
    Double getMin(); // todo maybe optimize do integer
    Double getMax();
}
