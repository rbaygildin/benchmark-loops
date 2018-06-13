package org.rbaygildin.launcher.utils;

import java.util.*;
import java.util.concurrent.RecursiveTask;

/**
 * Compute parallel adding of values in collection
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 */
public class ParallelAdder extends RecursiveTask<Integer> {

    private List<Integer> numbers_;

    public ParallelAdder(Collection<Integer> collection)
    {
        this.numbers_ = new ArrayList<>(collection);
    }

    @Override
    protected Integer compute()
    {
        if(numbers_.size() == 1)
            return numbers_.get(0);

        if(numbers_.size() < 1)
            return 0;
        int mid_ = numbers_.size() / 2;
        ParallelAdder adder1_ = new ParallelAdder(numbers_.subList(0, mid_));
        ParallelAdder adder2_ = new ParallelAdder(numbers_.subList(mid_, numbers_.size()));
        adder1_.fork();
        adder2_.fork();
        int a_ = adder1_.join();
        int b_ = adder2_.join();
        return a_ + b_;
    }
}
