package org.rbaygildin.config;

import org.rbaygildin.benchmark.BenchmarkLog;
import org.rbaygildin.benchmark.BenchmarkMeasurer;
import org.rbaygildin.controller.MainController;
import org.rbaygildin.custom_collection.AutoValueInitMap;
import org.rbaygildin.benchmark.BenchmarkLog;
import org.rbaygildin.benchmark.BenchmarkMeasurer;
import org.rbaygildin.custom_collection.AutoValueInitMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Configures application context and bean initialization, specifies path to components
 */
@Configuration
@ComponentScan("org.rbaygildin")
public class AppConfig {

    /**
     * Configure JDBC driver and register instance factories
     *
     * @throws ClassNotFoundException
     */
    @PostConstruct
    public void init() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        AutoValueInitMap.registerInstanceFactory(BenchmarkLog.class, BenchmarkLog::new);
    }

    /**
     * Create singleton instance of benchmark measurer
     *
     * @return Benchmark measurer
     */
    @Bean(name = "benchmarkMeasurer")
    public BenchmarkMeasurer benchmarkMeasurer(){
        BenchmarkMeasurer benchmarkMeasurer = new BenchmarkMeasurer();
        benchmarkMeasurer.turnOn();
        benchmarkMeasurer.clearLog();
        return benchmarkMeasurer;
    }
}
