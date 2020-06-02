package com.pnu.ordermanagementapp.service;

import com.pnu.ordermanagementapp.dto.user.UserRegistrationFormDto;
import com.pnu.ordermanagementapp.model.User;
import org.springframework.data.domain.Page;

public interface UserService {

    void create(UserRegistrationFormDto userRegistrationFormDto);

    Page<User> findAllUsers(Integer pageNumber, boolean reverseSort);

    Page<User> findUsersByName(Integer pageNumber, String nameQuery, boolean reverseSort);

    void deactivate(Long userId);

    void activate(Long userId);

}
