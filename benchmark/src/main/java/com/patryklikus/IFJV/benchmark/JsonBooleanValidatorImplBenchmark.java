package com.patryklikus.IFJV.benchmark;

import com.patryklikus.IFJV.library.schemas.models.BooleanSchema;
import com.patryklikus.IFJV.benchmark.states.JsonValidatorState;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

public class JsonBooleanValidatorImplBenchmark {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void booleanValidation(JsonValidatorState state, SchemaState schemaState) {
        state.jsonValidator.validate(schemaState.value, schemaState.schema);
    }

    @State(Scope.Benchmark)
    public static class SchemaState {
        @Param({" true ", " false "})
        public String value;
        public BooleanSchema schema;

        @Setup
        public void setUp() {
            schema = new BooleanSchema();
        }
    }
}
