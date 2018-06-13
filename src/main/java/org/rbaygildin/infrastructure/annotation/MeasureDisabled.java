package org.rbaygildin.infrastructure.annotation;

import java.lang.annotation.*;

/**
 * Prohibits a profiler to measure method execution time
 *
 * @author Yarnykh (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 *
 * @see Benchmark
 * @see TestMethod
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MeasureDisabled
{

}
