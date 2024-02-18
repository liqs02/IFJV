package com.patryklikus.IFJV.benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Launcher {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JsonBooleanValidatorImplBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}
