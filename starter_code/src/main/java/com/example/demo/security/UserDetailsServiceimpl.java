package com.example.demo.security;

import com.example.demo.Service.UserService;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserDetailsServiceimpl implements UserDetailsService {
   // UserService userService;

    UserRepository userRepository;

    public UserDetailsServiceimpl(UserService userService, UserRepository userRepository) {
     //   this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with Username: " + username + " not found");
        }

        System.out.println("User was found!: " + user.getUsername()+ " password: " + user.getPassword());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

}
