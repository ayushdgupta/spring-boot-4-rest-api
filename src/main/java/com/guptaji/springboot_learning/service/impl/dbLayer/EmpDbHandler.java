package com.guptaji.springboot_learning.service.impl.dbLayer;

import com.guptaji.springboot_learning.constant.Constants;
import com.guptaji.springboot_learning.entity.Employee;
import com.guptaji.springboot_learning.repositories.EmpRepo;
import com.guptaji.springboot_learning.service.impl.CacheInspectionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.guptaji.springboot_learning.constant.Constants.*;

@Component
public class EmpDbHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EmpDbHandler.class);

    @Autowired
    private EmpRepo empRepo;

    @Autowired
    private CacheInspectionServiceImpl cacheInspectionService;

    @Retryable(maxRetries = 3, delay = 1000)
    @CachePut(value = EMP_CACHE_NAME, key = "#emp.empId")
    public Employee persistEmployeeData(Employee emp) {
        LOG.info("saving employee {} in db", emp);
        Employee savedEmployee = empRepo.save(emp);
        return savedEmployee;
    }

    @Cacheable(value = Constants.EMP_CACHE_NAME, key = "#id")
    @Retryable(maxRetries = 3, delay = 1000)
    public Employee extractEmpById(Long id) {
        Optional<Employee> byId = empRepo.findById(id);
        LOG.info("Extracted employee data");
        return byId.orElse(null);
    }

    @Retryable(maxRetries = 3, delay = 1000)
    public List<Employee> extractAllEmployees() {
        List<Employee> all = empRepo.findAll();
        LOG.info("Extracted all employees from db");
        return all;
    }

    @Retryable(maxRetries = 3, delay = 1000)
    public int updateEmployeeOrg(Long empId, Long orgId) {
        int updatedCount = empRepo.updateOrgByEmpId(orgId, empId);
        cacheInspectionService.evictCache(EMP_CACHE_NAME, empId);
        // update the cache with latest data if you want in advance
        extractEmpById(empId);
        return updatedCount;
    }

    @Retryable(maxRetries = 3, delay = 1000)
    @CacheEvict(value = EMP_CACHE_NAME, key = "#id")
    public void deleteEmpWithId(Long id) {
        empRepo.deleteById(id);
        LOG.info("Data has been deleted");
    }

    @Retryable(maxRetries = 3, delay = 1000)
    @CachePut(value = EMP_CACHE_NAME, key = "#employee.empId")
    public Employee updateEmployee(Employee employee) {
        Employee savedEmployee = empRepo.save(employee);
        LOG.info("Employee has been updated");
        return savedEmployee;
    }
}
