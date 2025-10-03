package com.acadian.springboot.cruddemo.rest;

import com.acadian.springboot.cruddemo.Entity.Employee;
import com.acadian.springboot.cruddemo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;

    //to use patch method..
    private ObjectMapper objectMapper;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }


    @GetMapping("/employees/{employeeId}")
    public Employee getEmployeesById(@PathVariable int employeeId) {
        Employee employee = employeeService.findById(employeeId);

        if (employee == null) {
            throw new RuntimeException("Employee id not found :" + employeeId);
        }
        return employee;
    }

    @PostMapping("/employees")
    public Employee save(@RequestBody Employee employee) {
        employee.setId(0);
        return employeeService.save(employee);
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    //Patch method for partial update //by using objectmapper
    @PatchMapping("/employees/{employeeId}")
    public Employee patchEmployee(@PathVariable int employeeId, @RequestBody Map<String, Object> newEmpData) {

        Employee oldemployee = employeeService.findById(employeeId);
        if (oldemployee == null) {
            throw new RuntimeException("Employee Id not found : " + employeeId);
        }

        if (newEmpData.containsKey("id")) {
            throw new RuntimeException("Employee Id not allowed in Request Body");
        }

        Employee patchedEmployee = apply(newEmpData, oldemployee);


        return employeeService.save(patchedEmployee);

    }

    // Only using for Patch method
    private Employee apply(Map<String, Object> newEmpData, Employee oldemployee) {
        //comverting employee object to json object node
        ObjectNode oldEmpNode = objectMapper.convertValue(oldemployee, ObjectNode.class);

        //converting the new data into object node
        ObjectNode newEmpDataNode = objectMapper.convertValue(newEmpData, ObjectNode.class);

        oldEmpNode.setAll(newEmpDataNode);

        return objectMapper.convertValue(oldEmpNode, Employee.class);
    }

    //delete mthod
    @DeleteMapping("/employees/{employeeId}")
    public String deleteById(@PathVariable int employeeId) {
        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Employee id not found :" + employeeId);
        }
        employeeService.deleteById(employeeId);

        return "Delete Employee id :" + employeeId;
    }

}
