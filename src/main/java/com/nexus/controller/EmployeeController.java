package com.nexus.controller;

import com.nexus.constant.EmployeeConstant;
import com.nexus.dto.CreateEmployeeDTO;
import com.nexus.dto.EmployeeDTO;
import com.nexus.dto.UpdateEmployeeDTO;
import com.nexus.entity.Employee;
import com.nexus.entity.HttpResponse;
import com.nexus.service.EmployeeService;
import com.nexus.utils.ResponseUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;
    private ModelMapper mapper;
    @Autowired
    public EmployeeController(EmployeeService employeeService, ModelMapper mapper) {
        this.employeeService = employeeService;
        this.mapper = mapper;
    }

    @PostMapping("/create")
    @Operation(summary = "Create an Employee", description = "Save a new Employee data")
    private ResponseEntity<HttpResponse> createEmployee(@Valid @RequestBody CreateEmployeeDTO createEmployeeDTO){
        Employee employeeToSave = mapper.map(createEmployeeDTO, Employee.class);
        employeeService.createEmployee(employeeToSave);
        return ResponseUtility.buildResponse(EmployeeConstant.CREATION_DONE, CREATED);
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get all Employees", description = "Get all Employees in Database")
    private ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        List<Employee> employeesInDB = employeeService.getAllEmployees();
        List<EmployeeDTO> employeesToReturn = employeesInDB.stream().map(this::convertToEmployeeDTO).collect(Collectors.toList());
        return new ResponseEntity<>(employeesToReturn, OK);
    }

    @GetMapping("/get-employee/{id}")
    @Operation(summary = "Get an Employee", description = "Get a specific Employee By Id")
    private ResponseEntity getEmployeeById(@PathVariable("id") Long id){
        Employee employeeInDB = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(convertToEmployeeDTO(employeeInDB), OK);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a Employee", description = "Update a specific Employee fetched by Id")
    public ResponseEntity<HttpResponse> updateEmployee(@Valid @RequestBody UpdateEmployeeDTO updateEmployeeDTO, @PathVariable("id") Long id){
        employeeService.updateEmployee(mapper.map(updateEmployeeDTO, Employee.class), id);
        return ResponseUtility.buildResponse(EmployeeConstant.UPDATE_DONE, OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete an Employee", description = "Delete an Employee by Id")
    private ResponseEntity<HttpResponse> deleteEmployee(
            @Parameter(description = "Employee id field used to fetch a Employee and delete it")
            @PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseUtility.buildResponse(EmployeeConstant.DELETE_DONE, OK);
    }

    private EmployeeDTO convertToEmployeeDTO(Employee employee){
        return mapper.map(employee, EmployeeDTO.class);
    }

}
