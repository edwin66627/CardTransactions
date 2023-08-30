package com.nexus.service;

import com.nexus.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    void updateEmployee(Employee employee, Long id);
    void deleteEmployee(Long id);
}
