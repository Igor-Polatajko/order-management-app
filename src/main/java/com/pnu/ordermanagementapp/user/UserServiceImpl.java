package com.pnu.ordermanagementapp.user;

import com.pnu.ordermanagementapp.model.Role;
import com.pnu.ordermanagementapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

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
                .role(Role.USER)
                .active(true)
                .build();

        userRepository.save(userWithEncryptedPassword);
    }
}
