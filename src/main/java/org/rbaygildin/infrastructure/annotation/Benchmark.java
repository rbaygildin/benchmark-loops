package org.rbaygildin.infrastructure.annotation;

import org.rbaygildin.benchmark.BenchmarkMeasurer;
import org.rbaygildin.benchmark.IMeasurer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the application context to set up methods
 * unmarked annotation {@linkplain MeasureDisabled} measurement execution time
 * and the corresponding benchmark profiler
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 *
 * @see MeasureDisabled
 * @see TestMethod
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Benchmark
{
    Class<? extends IMeasurer> measurer() default BenchmarkMeasurer.class;
    String measurerName() default "";
    String message() default "";
}

//même-pressure