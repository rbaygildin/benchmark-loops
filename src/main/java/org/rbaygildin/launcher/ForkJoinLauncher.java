package org.rbaygildin.launcher;

import org.rbaygildin.benchmark.BenchmarkMeasurer;
import org.rbaygildin.dto.Employee;
import org.rbaygildin.infrastructure.annotation.Benchmark;
import org.rbaygildin.infrastructure.annotation.MeasureDisabled;
import org.rbaygildin.infrastructure.annotation.SelfInject;
import org.rbaygildin.infrastructure.annotation.TestMethod;
import org.rbaygildin.launcher.abstract_launcher.IStreamLauncher;
import org.rbaygildin.launcher.utils.*;
import org.rbaygildin.service.IDataService;
import org.rbaygildin.benchmark.BenchmarkMeasurer;
import org.rbaygildin.infrastructure.annotation.Benchmark;
import org.rbaygildin.infrastructure.annotation.MeasureDisabled;
import org.rbaygildin.infrastructure.annotation.SelfInject;
import org.rbaygildin.infrastructure.annotation.TestMethod;
import org.rbaygildin.launcher.abstract_launcher.IStreamLauncher;
import org.rbaygildin.launcher.utils.Correlation;
import org.rbaygildin.launcher.utils.ExtremumFinder;
import org.rbaygildin.launcher.utils.ParallelAdder;
import org.rbaygildin.launcher.utils.PrimeNumbersSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component("forkJoins")
@Benchmark(measurer = BenchmarkMeasurer.class, measurerName = "benchmarkMeasurer", message = "Fork/Join")
public class ForkJoinLauncher implements IStreamLauncher {

    @SelfInject
    private IStreamLauncher self;

    @Autowired
    private IDataService dataService;

    private Random random = new Random();

    @Override
    @TestMethod(testInfo = "Поиск простых чисел")
    public Collection<Integer> findPrimeNumbers(List<Integer> numbers)
    {
        ForkJoinPool pool = new ForkJoinPool(8);
        Collection<Integer> primes_ = pool.invoke(new PrimeNumbersSearcher(numbers, 8));
        pool.shutdown();
        return primes_;
    }

    @Override
    @TestMethod(testInfo = "Стандартные операции над числовыми коллекциями " +
            "(сортировка, суммирование, агрегация, фильтрация)")
    public void numColOperations()
    {
        List<Integer> list_ = new ArrayList<>();
        for(int i = 0; i < 1000; i++){
            list_.add(random.nextInt() * 2000 + 1);
        }

        //Сортировка числовой коллекции
        Collections.sort(list_);

        ForkJoinPool pool = new ForkJoinPool(8);
        Collection<Integer> primes_ = pool.invoke(new PrimeNumbersSearcher(list_, 4));
        int sum_ = pool.invoke(new ParallelAdder(primes_));
        int max_ = pool.invoke(new ExtremumFinder(primes_));
        pool.shutdown();
    }

    @Override
    @TestMethod(testInfo = "Вычисление корреляции между заработной платой и долгами")
    public double calculateCorrelation(List<Employee> employees)
    {
        ForkJoinPool pool_ = new ForkJoinPool(8);
        List<Pair<Double, Double>> data_ = employees.stream()
                .map(emp -> new Pair<>(emp.getSalary(), emp.getDebt()))
                .collect(Collectors.toList());
        Correlation cor_  = pool_.invoke(new ParallelCorrelator(data_, 8));
        pool_.shutdown();
        return cor_.calculate();
    }

    @Override
    @MeasureDisabled
    public void launch() {
        List<Integer> numbers_ = IntStream.range(1, 1001).boxed().collect(Collectors.toList());
        self.findPrimeNumbers(numbers_);
        self.numColOperations();
        self.calculateCorrelation(dataService.findEmployees());
    }
}
