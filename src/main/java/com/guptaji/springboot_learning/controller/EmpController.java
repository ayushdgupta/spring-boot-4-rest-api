package com.guptaji.springboot_learning.controller;

import com.guptaji.springboot_learning.entity.Employee;
import com.guptaji.springboot_learning.entity.User;
import com.guptaji.springboot_learning.service.impl.EmpServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/emp/v{version}", version = "1")
public class EmpController {

    private static final Logger LOG = LoggerFactory.getLogger(EmpController.class);

    @Autowired
    private EmpServiceImpl empService;

    @GetMapping(path = "/getAllEmployees")
    public ResponseEntity<?> extractEmployees(){
        LOG.info("Request received to extract all employees");
        List<Employee> allEmployee = empService.getAllEmployee();
        return new ResponseEntity<>(allEmployee, HttpStatus.OK);
    }

    @PostMapping(path = "/addEmployee")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee){
        LOG.info("Request received for adding an employee");
        Employee addedEmployee = empService.addEmployee(employee);
        return new ResponseEntity<>(addedEmployee, HttpStatus.CREATED);
    }

    @GetMapping(path = "/getById/{empId}")
    public ResponseEntity<?> getEmpById(@PathVariable("empId") Long id){
        LOG.info("Request received to fetch the employee with id {}", id);
        Employee employeeById = empService.getEmployeeById(id);
        if (employeeById == null){
            return new ResponseEntity<>("No Employee fond with this id "+id, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(employeeById, HttpStatus.OK);
        }
    }

    @PutMapping(path = "/updateOrgForEmp/{empId}/{orgId}")
    public ResponseEntity<?> updateOrgIdByEmpId(@PathVariable("empId") Long empId,
                                                @PathVariable("orgId") Long orgId){
        LOG.info("Request received to update the orgId {} for employee {}", orgId, empId);
        Employee employee = empService.updateEmployeeOrgById(empId, orgId);
        if (employee == null){
            return new ResponseEntity<>("No Employee fond with this id "+empId, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
    }

    @PutMapping(path = "/updateEmployee")
    public ResponseEntity<?> updateEmployeeDetails(@RequestBody Employee employee){
        LOG.info("Request received to update the employee {}", employee);
        Employee employeeById = empService.getEmployeeById(employee.getEmpId());
        if (employeeById == null){
            return new ResponseEntity<>("No Employee fond with this id "+employee.getEmpId(),
                    HttpStatus.NOT_FOUND);
        } else {
            Employee updatedEmployee = empService.updateEmployee(employee);
            return new ResponseEntity<>("Employee updated", HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "/deleteEmpById/{empId}")
    public ResponseEntity<?> deleteEmpById(@PathVariable("empId") Long empId){
        LOG.info("Request received for deleting the employee {}", empId);
        empService.deleteById(empId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/updateEmpName")
    public ResponseEntity<?> updateEmpName(@RequestBody Employee employee){
        LOG.info("Request received for updating the employee name {}", employee.getEmpId());
        Employee updatedEmployee = empService.updateEmployeeName(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }
}
