package com.guptaji.springboot_learning.repositories;

import com.guptaji.springboot_learning.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmpRepo extends JpaRepository<Employee, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "update employee SET org_id = :orgId where emp_id = :empId", nativeQuery = true)
    int updateOrgByEmpId(@Param("orgId") Long orgId, @Param("empId") Long empId);
}