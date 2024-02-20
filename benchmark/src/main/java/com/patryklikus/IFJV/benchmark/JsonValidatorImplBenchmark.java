/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.benchmark;

import com.patryklikus.IFJV.benchmark.states.JsonValidatorState;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;

public class JsonValidatorImplBenchmark {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void validationBenchmark(JsonValidatorState state, SchemaState schemaState) {
    }

    @State(Scope.Benchmark)
    public static class SchemaState {
        @Setup
        public void setUp() {
        }
    }
}
