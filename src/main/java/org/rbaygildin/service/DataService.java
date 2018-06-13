package org.rbaygildin.service;

import org.rbaygildin.dto.Employee;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Supplies client an data loaded from local SQL database
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 */
@Service
public class DataService implements IDataService{

    /**
     * Retrieve information about employees from database
     *
     * @return Information about employees
     */
    @Override
    public List<Employee> findEmployees()
    {
        String dbUrl = "jdbc:sqlite::resource:debt_stat.db";
        try (Connection conn = DriverManager.getConnection(dbUrl))
        {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from debt_stat");
            List<Employee> employees = new ArrayList<>();
            while(resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setAge(resultSet.getInt("age"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setDebt(resultSet.getDouble("debt"));
                employees.add(employee);
            }
            return employees;

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
