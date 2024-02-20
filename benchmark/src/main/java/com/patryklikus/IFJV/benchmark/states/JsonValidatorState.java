/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.benchmark.states;

import com.patryklikus.IFJV.library.validators.JsonValidator;
import com.patryklikus.IFJV.library.validators.JsonValidatorImpl;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class JsonValidatorState {
    public JsonValidator jsonValidator;

    @Setup
    public void setUp() {
        jsonValidator = new JsonValidatorImpl();
    }
}
