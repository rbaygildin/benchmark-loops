package org.rbaygildin.launcher.abstract_launcher;


import org.rbaygildin.dto.Employee;

import java.util.Collection;
import java.util.List;

import static java.lang.Math.sqrt;

public interface IStreamLauncher extends ILauncher {

    Collection<Integer> findPrimeNumbers(List<Integer> numbers);

    void numColOperations();

    double calculateCorrelation(List<Employee> employees);

    static boolean isPrime(int num){
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
