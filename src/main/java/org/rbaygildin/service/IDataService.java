package org.rbaygildin.service;

import org.rbaygildin.dto.Employee;

import java.util.List;

/**
 * Abstract data service
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 */
public interface IDataService {

    List<Employee> findEmployees();
}
