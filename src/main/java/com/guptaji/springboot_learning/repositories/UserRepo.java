package com.guptaji.springboot_learning.repositories;

import com.guptaji.springboot_learning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "update users SET name = :fullName where id =:id", nativeQuery = true)
    int updateNameById(@Param("fullName") String fullName, @Param("id") String id);
}
