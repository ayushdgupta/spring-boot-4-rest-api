package com.guptaji.springboot_learning.service.impl;

import com.guptaji.springboot_learning.entity.Employee;
import com.guptaji.springboot_learning.service.EmpService;
import com.guptaji.springboot_learning.service.impl.dbLayer.EmpDbHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    private static final Logger LOG = LoggerFactory.getLogger(EmpServiceImpl.class);

    @Autowired
    private EmpDbHandler empDbHandler;

    @Override
    public Employee addEmployee(Employee emp) {
        Employee newEmp = empDbHandler.persistEmployeeData(emp);
        LOG.info("Added employee {} in DB", newEmp.getEmpId());
        return newEmp;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        LOG.info("Extracting employee by id {}", id);
        Employee employee = empDbHandler.extractEmpById(id);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployee() {
        LOG.info("Fetching all employees from DB");
        List<Employee> employees = empDbHandler.extractAllEmployees();
        Collections.sort(employees);
        return employees;
    }

    @Override
    public Employee updateEmployeeOrgById(Long empId, Long orgId) {
        int count = empDbHandler.updateEmployeeOrg(empId, orgId);
        if (count == 0){
            LOG.info("No update because did n't found employee with id {}", empId);
            return null;
        } else {
            LOG.info("Updated the org id {} for employee {}", orgId, empId);
            return getEmployeeById(empId);
        }
    }

    @Override
    public void deleteById(Long id) {
        LOG.info("Deleting the data from db");
        empDbHandler.deleteEmpWithId(id);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        LOG.info("Updating employee");
        Employee emp = empDbHandler.updateEmployee(employee);
        return emp;
    }
}
