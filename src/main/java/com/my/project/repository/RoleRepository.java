package com.my.project.repository;

import com.my.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pi
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
