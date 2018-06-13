package org.rbaygildin.launcher;

import org.rbaygildin.benchmark.BenchmarkMeasurer;
import org.rbaygildin.dto.Employee;
import org.rbaygildin.infrastructure.annotation.Benchmark;
import org.rbaygildin.infrastructure.annotation.MeasureDisabled;
import org.rbaygildin.infrastructure.annotation.TestMethod;
import org.rbaygildin.infrastructure.annotation.SelfInject;
import org.rbaygildin.launcher.abstract_launcher.IStreamLauncher;
import org.rbaygildin.launcher.utils.Averager;
import org.rbaygildin.launcher.utils.Correlator;
import org.rbaygildin.launcher.utils.Pair;
import org.rbaygildin.service.IDataService;
import org.rbaygildin.benchmark.BenchmarkMeasurer;
import org.rbaygildin.infrastructure.annotation.Benchmark;
import org.rbaygildin.infrastructure.annotation.MeasureDisabled;
import org.rbaygildin.infrastructure.annotation.SelfInject;
import org.rbaygildin.infrastructure.annotation.TestMethod;
import org.rbaygildin.launcher.abstract_launcher.IStreamLauncher;
import org.rbaygildin.launcher.utils.Correlator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.sqrt;

@Component("streams")
@Benchmark(measurer = BenchmarkMeasurer.class, measurerName = "benchmarkMeasurer", message = "Streams")
public class StreamLauncher implements IStreamLauncher {

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
        Stream<Integer> stream_ = numbers.stream()
                .filter(IStreamLauncher::isPrime);
        return stream_.collect(Collectors.toSet());

        /*
        //Сортировка и фильтрация
        Stream<Integer> stream_ = numbers.stream()
                .sorted()
                .filter(IStreamLauncher::isPrime);

        //Суммирование
        int primeSum = stream_.reduce(0, (x, y) -> x + y);

        //Агрегация
        int maxPrime = stream_.max(Integer::compareTo).orElse(2);
        */
    }

    @Override
    @TestMethod(testInfo = "Стандартные операции над числовыми коллекциями " +
            "(сортировка, суммирование, агрегация, фильтрация)")
    public void numColOperations() {
        List<Integer> list_ = random.ints(2, 1001).limit(1000).boxed().collect(Collectors.toList());

        //Сортировка и фильтрация
        list_ = list_.stream()
                .sorted(Integer::compareTo)
                .filter(IStreamLauncher::isPrime)
                .collect(Collectors.toList());

        //Суммирование
        int sum_ = list_.stream().reduce(0, (x, y) -> x + y);

        //Агрегация
        int primeMax_ = list_.stream().max(Integer::compareTo).orElse(2);
    }

    @Override
    @TestMethod(testInfo = "Вычисление корреляции между заработной платой и долгами")
    public double calculateCorrelation(List<Employee> employees) {
        return employees.stream()
                .map(emp -> new Pair<>(emp.getSalary(), emp.getDebt()))
                .collect(Correlator::new, Correlator::accept, Correlator::combine).correlation();
    }
}
