package org.rbaygildin.launcher;


import org.rbaygildin.dto.Employee;
import org.rbaygildin.infrastructure.annotation.Benchmark;
import org.rbaygildin.infrastructure.annotation.MeasureDisabled;
import org.rbaygildin.infrastructure.annotation.TestMethod;
import org.rbaygildin.infrastructure.annotation.SelfInject;
import org.rbaygildin.benchmark.BenchmarkMeasurer;
import org.rbaygildin.service.IDataService;
import org.rbaygildin.launcher.abstract_launcher.IStreamLauncher;
import org.rbaygildin.benchmark.BenchmarkMeasurer;
import org.rbaygildin.infrastructure.annotation.Benchmark;
import org.rbaygildin.infrastructure.annotation.MeasureDisabled;
import org.rbaygildin.infrastructure.annotation.SelfInject;
import org.rbaygildin.infrastructure.annotation.TestMethod;
import org.rbaygildin.launcher.abstract_launcher.IStreamLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.Math.sqrt;

@Component("classicLoop")
@Benchmark(measurer = BenchmarkMeasurer.class, measurerName = "benchmarkMeasurer", message = "Classic loop")
public class ClassicLoopLauncher implements IStreamLauncher {

    @SelfInject
    private IStreamLauncher self;

    @Autowired
    private IDataService dataService;

    private Random random = new Random();

    @Override
    @MeasureDisabled
    public void launch() {
        List<Employee> employees_ = dataService.findEmployees();
        List<Integer> numbers_ = new ArrayList<>();
        for(int i = 2; i < 1000; i++){
            numbers_.add(i);
        }
        self.findPrimeNumbers(numbers_);
        self.numColOperations();
        self.calculateCorrelation(employees_);
    }

    @Override
    @TestMethod(testInfo = "Поиск простых чисел")
    public Collection<Integer> findPrimeNumbers(List<Integer> numbers) {
        Set<Integer> primes_ = new HashSet<>();
        for(int i = 0; i < numbers.size(); i++){
            int num = numbers.get(i);
            if(IStreamLauncher.isPrime(num)){
                primes_.add(num);
            }
        }
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
        for (int i = 0; i < list_.size(); i++)
            sum_ += list_.get(i);

        //Фильтрация
        int maxPrime_ = Integer.MIN_VALUE;
        for(int i = 0; i < list_.size(); i++){
            if(!IStreamLauncher.isPrime(list_.get(i)))
                list_.remove(i);
        }

        //Агрегация
        for(int i = 0; i < list_.size(); i++){
            if(maxPrime_ < list_.get(i))
                maxPrime_ = list_.get(i);
        }
    }

    @Override
    @TestMethod(testInfo = "Вычисление корреляции между заработной платой и долгами")
    public double calculateCorrelation(List<Employee> employees)
    {
        double mean_x, mean_y, mean_xy, sigma_x, sigma_y, x_sq, y_sq;
        mean_x = mean_y = mean_xy = 0;
        x_sq = y_sq = 1;
        int n = employees.size();
        for(int i = 0; i < n; i++){
            mean_x += employees.get(i).getSalary();
            mean_y += employees.get(i).getDebt();
            mean_xy += employees.get(i).getSalary() * employees.get(i).getDebt();
            x_sq *= employees.get(i).getSalary();
            y_sq *= employees.get(i).getDebt();
        }
        sigma_x = sqrt(n * x_sq - mean_x * mean_x);
        sigma_y = sqrt(n * y_sq - mean_y * mean_y);
        return (n * mean_xy - mean_x * mean_y) / (sigma_x * sigma_y);
    }
}
