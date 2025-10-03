package com.acadian.springboot.cruddemo.Dao;

import com.acadian.springboot.cruddemo.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
