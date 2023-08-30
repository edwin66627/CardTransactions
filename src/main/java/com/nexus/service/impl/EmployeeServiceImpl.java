package com.nexus.service.impl;

import com.nexus.constant.EmployeeConstant;
import com.nexus.entity.Employee;
import com.nexus.repository.EmployeeRepository;
import com.nexus.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BCryptPasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        Calendar currentDate = Calendar.getInstance();
        employee.setHireDate(currentDate.getTime());
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id){
        Employee employeeInDB = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format(EmployeeConstant.NO_SUCH_ELEMENT, "id", id)));

        return employeeInDB;
    }

    public void updateEmployee(Employee employee, Long id){
        Employee employeeInDB = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format(EmployeeConstant.NO_SUCH_ELEMENT, "id", id)));

        employeeInDB.setFirstName(employee.getFirstName());
        employeeInDB.setLastName(employee.getLastName());
        employeeInDB.setAddress(employee.getAddress());
        employeeRepository.save(employeeInDB);
    }

    public void deleteEmployee(Long id){
        Employee employeeInDB = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format(EmployeeConstant.NO_SUCH_ELEMENT, "id", id)));
        employeeRepository.deleteById(id);
    }
}
