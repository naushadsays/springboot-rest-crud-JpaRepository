package com.acadian.springboot.cruddemo.service;

import com.acadian.springboot.cruddemo.Entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findById(int id);
    Employee save(Employee employee);
    void deleteById(int id);
}
