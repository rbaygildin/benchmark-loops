package org.rbaygildin.launcher.utils;

import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

import static java.lang.Math.sqrt;

/**
 * Searches for prime numbers in source collection
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 */
public class PrimeNumbersSearcher extends RecursiveTask<Collection<Integer>>{

    private List<Integer> numbers_;
    private int minLen_ = 1;


    public PrimeNumbersSearcher(Collection<Integer> numbers)
    {
        this.numbers_ = new ArrayList<>(numbers);
    }

    public PrimeNumbersSearcher(Collection<Integer> numbers, int minLen)
    {
        this(numbers);
        this.minLen_ = minLen;
    }

    @Override
    protected Collection<Integer> compute()
    {
        if(numbers_.size() <= minLen_)
            return numbers_.stream()
                    .filter(this::isPrime)
                    .collect(Collectors.toSet());
        int mid_ = numbers_.size() / 2;
        PrimeNumbersSearcher task1_ = new PrimeNumbersSearcher(numbers_.subList(0, mid_), minLen_);
        PrimeNumbersSearcher task2_ = new PrimeNumbersSearcher(numbers_.subList(mid_, numbers_.size()));
        task1_.fork();
        task2_.fork();
        Set<Integer> res_;
        res_ = new TreeSet<>(task1_.join());
        res_.addAll(task2_.join());
        return res_;
    }

    private boolean isPrime(int num){
        if(num <= 2)
            return true;
        int d = 2;
        while(d <= (long) sqrt(num)){
            if(num % d == 0)
                return false;
            d++;
        }
        return true;
    }
}
