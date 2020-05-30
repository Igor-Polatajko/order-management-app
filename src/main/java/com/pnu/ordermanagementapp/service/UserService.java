package com.pnu.ordermanagementapp.service;

import com.pnu.ordermanagementapp.model.User;
import org.springframework.data.domain.Page;

public interface UserService {

    void create(User user);

    Page<User> findAllUsers(Integer pageNumber, boolean reverseSort);

    Page<User> findUsersByName(Integer pageNumber, String nameQuery, boolean reverseSort);

    void delete(Long userId);

    void activate(Long userId);

}
