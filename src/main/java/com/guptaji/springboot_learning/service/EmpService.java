package com.guptaji.springboot_learning.service;

import com.guptaji.springboot_learning.entity.Employee;

import java.util.List;

public interface EmpService {

    Employee addEmployee(Employee emp);
    Employee getEmployeeById(Long id);
    List<Employee> getAllEmployee();
    Employee updateEmployeeOrgById(Long id, Long orgId);
    void deleteById(Long id);

    Employee updateEmployee(Employee employee);
}
