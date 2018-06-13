package org.rbaygildin.unit_testing.config;

import org.rbaygildin.benchmark.BenchmarkMeasurer;
import org.rbaygildin.benchmark.BenchmarkMeasurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configures unit tests context
 */
@Configuration
@ComponentScan(value = {"org.rbaygildin.launcher", "org.rbaygildin.service"})
public class UnitTestingConfig {

    /**
     * Disable benchmark measurer
     *
     * @return Benchmark measurer
     */
    @Bean(name = "benchmarkMeasurer")
    public BenchmarkMeasurer benchmarkMeasurer(){
        BenchmarkMeasurer benchmarkMeasurer = new BenchmarkMeasurer();
        benchmarkMeasurer.turnOff();
        return benchmarkMeasurer;
    }
}
