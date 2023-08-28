package com.nexus.controller;

import com.nexus.constant.EmployeeConstant;
import com.nexus.dto.CreateEmployeeDTO;
import com.nexus.entity.Employee;
import com.nexus.entity.HttpResponse;
import com.nexus.service.EmployeeService;
import com.nexus.utils.ResponseUtility;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

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

}
