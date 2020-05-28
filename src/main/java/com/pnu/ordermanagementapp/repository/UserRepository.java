package com.pnu.ordermanagementapp.repository;

import com.pnu.ordermanagementapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
