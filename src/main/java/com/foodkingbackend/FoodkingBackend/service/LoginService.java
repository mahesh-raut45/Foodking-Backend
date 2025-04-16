package com.foodkingbackend.FoodkingBackend.service;

import com.foodkingbackend.FoodkingBackend.entity.User;
import com.foodkingbackend.FoodkingBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    UserRepository loginRepository;

    private User user;


//    public String loginUser(User user) throws Exception {
//
//        String userName = String.valueOf(loginRepository.findById(user.getUserName()));
//        String password = String.valueOf(loginRepository.findById(user.getPassword()));
//
//        if (userName.isEmpty() && password.isEmpty()) {
//            throw new Exception("Invalid User");
//        } else {
//            return "Login Success";
//        }
//
//    }
}
