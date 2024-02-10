package com.patryklikus.ifjv.benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class JsonValidatorBenchmark {
    @Benchmark
    public void testMethod() {
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            sum += i;
        }
    }

    public static void main(String[] args) throws Exception {
        new Runner(new OptionsBuilder()
                .include(JsonValidatorBenchmark.class.getSimpleName())
                .forks(1)
                .build())
                .run();
    }
}
