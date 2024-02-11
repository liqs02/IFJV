/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.schemas;


public interface ArraySchema {
    Integer getMinSize();

    Integer getMaxSize();

    Schema getItems();
}
