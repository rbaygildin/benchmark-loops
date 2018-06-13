package org.rbaygildin.launcher.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Computes correlation between two data sets
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 */
public class ParallelCorrelator extends RecursiveTask<Correlation>
{

    private List<Pair<Double, Double>> data_;
    private int minLen_ = 2;

    public ParallelCorrelator(Collection<Pair<Double, Double>> dataSet, int minLen)
    {
        data_ = new ArrayList<>(dataSet);
        this.minLen_ = minLen;
    }

    @Override
    protected Correlation compute() {
        if(data_.size() <= minLen_){
            Correlation cor_ = new Correlation(data_.size());
            for (Pair<Double, Double> pair_ : data_) {
                double x_, y_;
                x_ = pair_.getFirst();
                y_ = pair_.getSecond();
                cor_.setX_Total(cor_.getX_Total() + x_);
                cor_.setY_Total(cor_.getY_Total() + y_);
                cor_.setXY_Total(cor_.getXY_Total() + x_ * y_);
                cor_.setXSQ_Total(cor_.getXSQ_Total() + x_ * x_);
                cor_.setYSQ_Total(cor_.getYSQ_Total() + y_ * y_);
            }
            return cor_;
        }
        int mid_ = data_.size() / 2;
        ParallelCorrelator task1_ = new ParallelCorrelator(data_.subList(0, mid_), minLen_);
        ParallelCorrelator task2_ = new ParallelCorrelator(data_.subList(mid_, data_.size()), minLen_);
        task1_.fork();
        task2_.fork();
        Correlation cor_ = new Correlation(data_.size());
        Correlation c1_ = task1_.join();
        Correlation c2_ = task2_.join();
        cor_.setX_Total(c1_.getX_Total() + c2_.getX_Total());
        cor_.setY_Total(c1_.getY_Total() + c2_.getY_Total());
        cor_.setXY_Total(c1_.getXY_Total() + c2_.getXY_Total());
        cor_.setXSQ_Total(c1_.getXSQ_Total() + c2_.getXSQ_Total());
        cor_.setYSQ_Total(c1_.getYSQ_Total() + c2_.getYSQ_Total());
        return cor_;
    }
}
