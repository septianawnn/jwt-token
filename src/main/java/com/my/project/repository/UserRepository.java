package com.my.project.repository;

import com.my.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pi
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername (String username);
}
