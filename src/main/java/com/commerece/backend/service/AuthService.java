package com.commerece.backend.service;

import com.commerece.backend.model.User;
import com.commerece.backend.repository.UserRepository;
import com.commerece.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register a new user
    public ResponseEntity<?> register(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {  // Checking if email exists
            return ResponseEntity.badRequest().body("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encoding password
        userRepo.save(user);  // Saving the user
        String token = jwtUtil.generateToken(user.getEmail());  // Generate JWT token
        return ResponseEntity.ok(Map.of("token", token));  // Returning token in response
    }

    // Login user and validate credentials
    public ResponseEntity<?> login(User user) {
        Optional<User> dbUserOpt = userRepo.findByEmail(user.getEmail());  // Fetch user by email
        if (dbUserOpt.isEmpty() || !passwordEncoder.matches(user.getPassword(), dbUserOpt.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail());  // Generate JWT token
        return ResponseEntity.ok(Map.of("token", token));  // Returning token in response
    }
}
