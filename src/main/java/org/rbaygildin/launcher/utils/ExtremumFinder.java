package org.rbaygildin.launcher.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Finds maximum (minimum) in source collection
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 */
public class ExtremumFinder extends RecursiveTask<Integer>{

    private List<Integer> list_;

    public ExtremumFinder(Collection<Integer> collection)
    {
        this.list_ = new ArrayList<>(collection);
    }

    @Override
    protected Integer compute()
    {
        if(list_.size() == 1)
            return list_.get(0);

        if(list_.size() < 1)
            return Integer.MIN_VALUE;

        int mid_ = list_.size() / 2;

        ExtremumFinder task1_ = new ExtremumFinder(list_.subList(0, mid_));
        ExtremumFinder task2_ = new ExtremumFinder(list_.subList(mid_, list_.size()));
        task1_.fork();
        task2_.fork();
        int a_ = task1_.join();
        int b_ = task2_.join();
        return a_ > b_ ? a_ : b_;
    }
}
