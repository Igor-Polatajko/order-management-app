package com.pnu.ordermanagementapp.repository;

import com.pnu.ordermanagementapp.model.Role;
import com.pnu.ordermanagementapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);

    Page<User> findByRole(Role role, Pageable pageable);

    Page<User> findByFirstNameContainsOrLastNameContainsOrUsernameContainsAndRole(String firstName,
                                                                                  String lastName,
                                                                                  String username,
                                                                                  Role role, Pageable pageable);

}
