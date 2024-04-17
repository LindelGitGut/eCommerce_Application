package com.example.demo.Service;


import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {



    @Autowired
    UserRepository userRepository;


    public void saveUser(User user){
        userRepository.save(user);
    }


    public User findUserByName(String name){
        return userRepository.findByUsername(name);
    }

}
