package org.rbaygildin.infrastructure.annotation;

import java.lang.annotation.*;

/**
 * Provides profiler log information on the method
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 *
 * @see Benchmark
 * @see MeasureDisabled
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestMethod
{
    String testInfo() default "";
}
