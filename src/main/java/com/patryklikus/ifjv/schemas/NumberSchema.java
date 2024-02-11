/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;

public interface NumberSchema {
    boolean isRequired();
    Double getMin(); // todo maybe optimize do integer
    Double getMax();
}
