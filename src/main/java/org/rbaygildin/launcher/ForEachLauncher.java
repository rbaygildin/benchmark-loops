package org.rbaygildin.launcher;

import org.rbaygildin.dto.Employee;
import org.rbaygildin.infrastructure.annotation.Benchmark;
import org.rbaygildin.infrastructure.annotation.MeasureDisabled;
import org.rbaygildin.infrastructure.annotation.TestMethod;
import org.rbaygildin.infrastructure.annotation.SelfInject;
import org.rbaygildin.benchmark.BenchmarkMeasurer;
import org.rbaygildin.launcher.abstract_launcher.IStreamLauncher;
import org.rbaygildin.service.IDataService;
import org.rbaygildin.benchmark.BenchmarkMeasurer;
import org.rbaygildin.infrastructure.annotation.Benchmark;
import org.rbaygildin.infrastructure.annotation.MeasureDisabled;
import org.rbaygildin.infrastructure.annotation.SelfInject;
import org.rbaygildin.infrastructure.annotation.TestMethod;
import org.rbaygildin.launcher.abstract_launcher.IStreamLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;

@Component("forEach")
@Benchmark(measurer = BenchmarkMeasurer.class, measurerName = "benchmarkMeasurer", message = "Foreach")
public class ForEachLauncher implements IStreamLauncher {

    @SelfInject
    private IStreamLauncher self;

    @Autowired
    private IDataService dataService;

    private Random random = new Random();

    @Override
    @MeasureDisabled
    public void launch() {
        List<Integer> numbers_ = IntStream.range(1, 1001).boxed().collect(Collectors.toList());
        self.findPrimeNumbers(numbers_);
        self.numColOperations();
        self.calculateCorrelation(dataService.findEmployees());
    }

    @Override
    @TestMethod(testInfo = "Поиск простых чисел")
    public Collection<Integer> findPrimeNumbers(List<Integer> numbers) {
        Set<Integer> primes_ = new HashSet<>();
        numbers.forEach(num -> {
            if(IStreamLauncher.isPrime(num)){
                primes_.add(num);
            }
        });
        return primes_;
    }

    @Override
    @TestMethod(testInfo = "Стандартные операции над числовыми коллекциями " +
            "(сортировка, суммирование, агрегация, фильтрация)")
    public void numColOperations() {
        List<Integer> list_ = new ArrayList<>();
        for(int i = 0; i < 1000; i++){
            list_.add(random.nextInt() * 2000 + 1);
        }

        //Сортировка числовой коллекции
        Collections.sort(list_);

        //Суммирование числовой коллекции
        int sum_ = 0;
        for (int num : list_)
            sum_ += num;

        //Фильтрация
        int maxPrime_ = Integer.MIN_VALUE;
        Iterator<Integer> it_ = list_.iterator();
        while(it_.hasNext()){
            Integer num = it_.next();
            if(!IStreamLauncher.isPrime(num))
               it_.remove();
        }

        //Агрегация
        for(int num : list_){
            if(maxPrime_ < num)
                maxPrime_ = num;
        }
    }

    @Override
    @TestMethod(testInfo = "Вычисление корреляции между заработной платой и долгами")
    public double calculateCorrelation(List<Employee> employees) {
        double mean_x, mean_y, mean_xy, sigma_x, sigma_y, x_sq, y_sq;
        mean_x = mean_y = mean_xy = 0;
        x_sq = y_sq = 1;
        int n = employees.size();
        for(Employee emp : employees){
            mean_x += emp.getSalary();
            mean_y += emp.getDebt();
            mean_xy += emp.getSalary() * emp.getDebt();
            x_sq *= emp.getSalary();
            y_sq *= emp.getDebt();
        }
        sigma_x = sqrt(n * x_sq - mean_x * mean_x);
        sigma_y = sqrt(n * y_sq - mean_y * mean_y);
        return (n * mean_xy - mean_x * mean_y) / (sigma_x * sigma_y);
    }
}
