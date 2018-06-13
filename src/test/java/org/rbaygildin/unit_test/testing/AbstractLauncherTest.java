package org.rbaygildin.unit_test.testing;

import org.rbaygildin.dto.Employee;
import org.rbaygildin.launcher.abstract_launcher.IStreamLauncher;
import org.rbaygildin.service.IDataService;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

abstract class AbstractLauncherTest {

    void __testFindPrimeNumbers(IStreamLauncher streamLauncher){
        List<Integer> numbers_ = IntStream.range(1, 100).boxed().collect(Collectors.toList());
        Collection<Integer> primes1_ = numbers_.stream().filter(this::__isPrime).collect(Collectors.toList());
        Collection<Integer> primes2_ = streamLauncher.findPrimeNumbers(numbers_);
        assertEquals(primes1_.size(), primes2_.size());
        assertTrue(primes1_.containsAll(primes2_));
    }

    void __testCalculateCorrelation(IDataService dataService, IStreamLauncher sl){
        List<Employee> employees = dataService.findEmployees();
        PearsonsCorrelation cor = new PearsonsCorrelation();
        double[] x_ = employees.stream()
                .mapToDouble(Employee::getSalary)
                .toArray();
        double[] y_ = employees.stream()
                .mapToDouble(Employee::getDebt)
                .toArray();
        assertTrue(abs(cor.correlation(x_, y_) - sl.calculateCorrelation(employees)) <= 0.1);
    }

    boolean __isPrime(int num){
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
