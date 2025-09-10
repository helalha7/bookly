package com.dev.bookly.security.services;

import com.dev.bookly.user.domains.User;
import com.dev.bookly.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServicesImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserDetailsServicesImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        System.out.println(user.getAccount().getUsername());
        return new UserDetailsImpl(user);
    }
}
