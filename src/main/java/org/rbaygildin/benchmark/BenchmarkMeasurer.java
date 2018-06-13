package org.rbaygildin.benchmark;

import org.rbaygildin.infrastructure.annotation.Benchmark;
import org.rbaygildin.infrastructure.annotation.MeasureDisabled;
import org.rbaygildin.infrastructure.annotation.TestMethod;
import org.rbaygildin.custom_collection.AutoValueInitMap;
import org.rbaygildin.exceptions.BenchmarkDisabledException;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.rbaygildin.custom_collection.AutoValueInitMap;
import org.rbaygildin.exceptions.BenchmarkDisabledException;
import org.rbaygildin.infrastructure.annotation.Benchmark;
import org.rbaygildin.infrastructure.annotation.MeasureDisabled;
import org.rbaygildin.infrastructure.annotation.TestMethod;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Measure execution time of methods,
 * create a report with statistics and clear logs
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 *
 * @see Benchmark
 * @see MeasureDisabled
 * @see TestMethod
 */
@Component
public class BenchmarkMeasurer implements IBenchmarkMeasurer{

    private boolean enabled_;

    private DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy");
    private final Map<Class, BenchmarkLog> benchmarkLog = new AutoValueInitMap<>(BenchmarkLog.class);
    private long timeBefore_;

    /**
     * Begin measurement
     *
     * @throws BenchmarkDisabledException
     */
    @Override
    public void beginBenchmark() throws BenchmarkDisabledException{
        if(!enabled_)
            throw new BenchmarkDisabledException();
        synchronized (benchmarkLog){
            timeBefore_ = System.nanoTime();
        }
    }

    /**
     * Stop measurement
     *
     * @param o Source object
     * @param method Method
     * @throws BenchmarkDisabledException
     */
    @Override
    public void stopBenchmark(Object o, Method method) throws BenchmarkDisabledException {
        if(!enabled_)
            throw new BenchmarkDisabledException();
        synchronized (benchmarkLog){
            long timeAfter_ = System.nanoTime();
            String message_;
            TestMethod testMethod = method.getAnnotation(TestMethod.class);
            if (testMethod != null) {
                message_ = testMethod.testInfo();
            }
            else{
                message_ = method.getName() + "() was tested";
            }
            Benchmark benchmarkAnn = o.getClass().getAnnotation(Benchmark.class);
            if (benchmarkAnn != null) {
                benchmarkLog.get(o.getClass()).setMessage(benchmarkAnn.message());
            }
            else{
                benchmarkLog.get(o.getClass()).setMessage(o.getClass().getName());
            }
            MethodTestLog methodTestLog = new MethodTestLog();
            methodTestLog.setMessage(message_);
            methodTestLog.setOwner(o.getClass());
            methodTestLog.setMethod(method);
            methodTestLog.setBenchmarkDate(LocalDate.now());
            methodTestLog.setTotalTime(timeAfter_ - timeBefore_);
            benchmarkLog.get(o.getClass()).getMethodTestLogs().add(methodTestLog);
        }
    }

    /**
     * Clear benchmark log
     */
    @Override
    public void clearLog() {
        benchmarkLog.clear();
    }

    /**
     * Retrieve benchmark report with statistics
     * @return Report
     */
    @Override
    public Map<Class, BenchmarkLog> retrieveReport() {
        return benchmarkLog;
    }

    /**
     * Enable measurer
     */
    @Override
    public void turnOn() {
        enabled_ = true;
    }

    /**
     * Disable measurer
     */
    @Override
    public void turnOff() {
        enabled_ = false;
    }

    /**
     * Check if measurer is enabled
     * @return Status
     */
    @Override
    public boolean isEnabled() {
        return enabled_;
    }
}
