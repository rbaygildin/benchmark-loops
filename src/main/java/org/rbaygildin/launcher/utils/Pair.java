package org.rbaygildin.launcher.utils;


public class Pair<T1, T2> {

    private T1 value1_;
    private T2 value2_;

    public void setFirst(T1 first){
        value1_ = first;
    }
    public T1 getFirst(){
        return value1_;
    }
    public void setSecond(T2 second){
        value2_ = second;
    }
    public T2 getSecond(){
        return value2_;
    }

    public Pair(T1 first, T2 second){
        value1_ = first;
        value2_ = second;
    }
}
