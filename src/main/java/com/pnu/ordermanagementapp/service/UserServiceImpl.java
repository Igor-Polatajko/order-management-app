package com.pnu.ordermanagementapp.service;

import com.pnu.ordermanagementapp.exception.ServiceException;
import com.pnu.ordermanagementapp.model.Role;
import com.pnu.ordermanagementapp.model.User;
import com.pnu.ordermanagementapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private static final int PAGE_SIZE = 15;

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (isNull(user)) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }

    @Override
    public void create(User user) {

        User userWithEncryptedPassword = user.toBuilder()
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .role(Role.ROLE_USER)
                .active(true)
                .build();

        userRepository.save(userWithEncryptedPassword);
    }

    @Override
    public Page<User> findAllUsers(Integer pageNumber, boolean reverseSort) {

        Pageable pageable = createPageable(pageNumber, reverseSort);
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findUsersByName(Integer pageNumber, String nameQuery, boolean reverseSort) {
        Pageable pageable = createPageable(pageNumber, reverseSort);
        return userRepository
                .findByFirstNameContainsOrLastNameContainsOrUsernameContains(nameQuery, nameQuery,
                        nameQuery, pageable);
    }

    @Override
    public void deactivate(Long userId) {

        User user = findUserByIdOrThrowException(userId);
        user = user.toBuilder().active(false).build();
        userRepository.save(user);
    }

    @Override
    public void activate(Long userId) {

        User user = findUserByIdOrThrowException(userId);
        user = user.toBuilder().active(true).build();
        userRepository.save(user);
    }

    private User findUserByIdOrThrowException(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ServiceException("User not found!"));
    }

    private Pageable createPageable(int pageNumber, boolean reverseSort) {

        if (pageNumber < 1) {
            throw new ServiceException("Incorrect page number!");
        }

        if (reverseSort) {
            return PageRequest.of(pageNumber - 1, PAGE_SIZE,
                    Sort.by("active").ascending().and(Sort.by("id").ascending()));
        } else {
            return PageRequest.of(pageNumber - 1, PAGE_SIZE,
                    Sort.by("active").descending().and(Sort.by("id").ascending()));
        }
    }
}
