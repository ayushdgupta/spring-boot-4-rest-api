package com.guptaji.springboot_learning.repositories;

import com.guptaji.springboot_learning.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgRepo extends JpaRepository<Organization, Long> {
}
