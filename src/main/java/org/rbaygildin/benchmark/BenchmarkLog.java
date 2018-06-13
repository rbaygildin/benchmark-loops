package org.rbaygildin.benchmark;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkLog {

    public List<MethodTestLog> getMethodTestLogs() {
        return methodTestLogs;
    }

    public void setMethodTestLogs(List<MethodTestLog> methodTestLogs) {
        this.methodTestLogs = methodTestLogs;
    }

    private List<MethodTestLog> methodTestLogs = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
