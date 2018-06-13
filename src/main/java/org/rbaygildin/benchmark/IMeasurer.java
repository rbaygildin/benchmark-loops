package org.rbaygildin.benchmark;


import org.rbaygildin.infrastructure.annotation.Benchmark;
import org.rbaygildin.infrastructure.annotation.MeasureDisabled;
import org.rbaygildin.infrastructure.annotation.TestMethod;
import org.rbaygildin.exceptions.BenchmarkDisabledException;
import org.rbaygildin.exceptions.BenchmarkDisabledException;
import org.rbaygildin.infrastructure.annotation.Benchmark;
import org.rbaygildin.infrastructure.annotation.MeasureDisabled;
import org.rbaygildin.infrastructure.annotation.TestMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * It defines common functionality of benchmark profilers
 * for measuring execution time of methods,
 * create a report with statistics and clear logs
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 *
 * @see Benchmark
 * @see MeasureDisabled
 * @see TestMethod
 */
public interface IMeasurer
{
    /**
     * Measure execution of time and return value after execution
     *
     * @param o Original source object
     * @param method Method
     * @param args Arguments
     * @return Value of execution
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws BenchmarkDisabledException
     */
    Object measure(Object o, Method method, Object ... args) throws InvocationTargetException, IllegalAccessException, BenchmarkDisabledException;

    /**
     * Clear benchmark log
     */
    void clearLog();

    /**
     * Retrieve benchmark report with statistics
     *
     * @return Report
     */
    Map<Class, BenchmarkLog> retrieveReport();

    /**
     * Enable measurer
     */
    void turnOn();

    /**
     * Disable measurer
     */
    void turnOff();

    /**
     * Check if measurer is enabled
     *
     * @return Status
     */
    boolean isEnabled();
}
