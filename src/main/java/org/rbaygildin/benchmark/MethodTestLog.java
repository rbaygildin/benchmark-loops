package org.rbaygildin.benchmark;

import org.joda.time.LocalDate;

import java.lang.reflect.Method;

public class MethodTestLog {

    public Class getOwner() {
        return owner;
    }

    public void setOwner(Class owner) {
        this.owner = owner;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public LocalDate getBenchmarkDate() {
        return benchmarkDate;
    }

    public void setBenchmarkDate(LocalDate benchmarkDate) {
        this.benchmarkDate = benchmarkDate;
    }

    private Class owner;
    private Method method;
    private String message;
    private long totalTime;
    private LocalDate benchmarkDate;
}
