package com.foodkingbackend.FoodkingBackend.controllers;

import com.foodkingbackend.FoodkingBackend.entity.User;
import com.foodkingbackend.FoodkingBackend.repository.UserRepository;
import com.foodkingbackend.FoodkingBackend.requestAndResponseModels.JwtAuthResponse;
import com.foodkingbackend.FoodkingBackend.requestAndResponseModels.LoginRequest;
import com.foodkingbackend.FoodkingBackend.requestAndResponseModels.MessageResponse;
import com.foodkingbackend.FoodkingBackend.requestAndResponseModels.RegisterRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
@CrossOrigin
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @GetMapping("/user")
//    public ResponseEntity<?> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
//        if (userDetails == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
//        }
//        Optional<User> optionalUser = userRepository.findByUserName(userDetails.getUsername());
//        if (optionalUser.isEmpty()) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found"));
//        }
//
//        User user = optionalUser.get();
//        return ResponseEntity.ok(user);
//    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {

        log.info("User details received for registrations: {}", request);
        //check if username is already taken
        if (userRepository.existsByUserName(request.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken"));
        }
        //check if email is already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already exists");
        }

        // create new user
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(new Date());
        //save user
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error occurred while registering the user:", e);
        }
        log.info("User registered successfully!");
        return ResponseEntity.ok(new MessageResponse(("User Registered Successfully")));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user;
        try {
//        Find user by  username
            Optional<User> optionalUser = userRepository.findByUserName(loginRequest.getUserName());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found"));
            }

            user = optionalUser.get();
//        password check
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Incorrect password"));
            }
        } catch (Exception e) {
            log.error("Error occurred while login user :", e);
            throw new RuntimeException(e);
        }


        return ResponseEntity.ok(new JwtAuthResponse("fake-jwt-token", user.getId(), user.getUserName(), user.getEmail()));

//        if user exist, return user details
//        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
//        jwtAuthResponse.setUserName(user.getUserName());
//        jwtAuthResponse.setEmail(user.getEmail());
//
//        return ResponseEntity.ok(jwtAuthResponse);

    }
}

