package org.rbaygildin.launcher.utils;

import java.util.function.DoubleConsumer;

/**
 * Compute average of data set
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 */
public class Averager implements DoubleConsumer{

    private int count_ = 0;
    private double total_ = 0;

    @Override
    public void accept(double value) {
        count_++;
        total_ += value;
    }

    public void combine(Averager other){
        count_ += other.count_;
        total_ += other.total_;
    }

    public double average(){
        return count_ > 0 ? total_ / count_ : 0;
    }
}
