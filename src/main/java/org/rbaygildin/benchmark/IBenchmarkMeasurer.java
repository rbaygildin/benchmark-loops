package org.rbaygildin.benchmark;


import org.rbaygildin.exceptions.BenchmarkDisabledException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

interface IBenchmarkMeasurer extends IMeasurer {

    /**
     * Begin measurement
     *
     * @throws BenchmarkDisabledException
     */
    void beginBenchmark() throws BenchmarkDisabledException;

    /**
     * Stop measurement
     *
     * @param o Source object
     * @param method Method
     * @throws BenchmarkDisabledException
     */
    void stopBenchmark(Object o, Method method) throws BenchmarkDisabledException;

    @Override
    default Object measure(Object o, Method method, Object... args) throws InvocationTargetException, IllegalAccessException, BenchmarkDisabledException{
        Object retVal;
        beginBenchmark();
        retVal = method.invoke(o, args);
        stopBenchmark(o, method);
        return retVal;
    }
}
