package com.commerece.backend.service;

import com.commerece.backend.model.User;  // Assuming you have a User model class.
import com.commerece.backend.repository.UserRepository;  // Repository to fetch users

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;  // Assuming you are using a repository to fetch users.

    // Inject the UserRepository using constructor injection
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the database using the username (email in this case)
        Optional<User> userOptional = userRepository.findByEmail(username);

        // If the user is not found, throw an exception
        User user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + username));

        // Assuming user has a list of roles, and roles are stored in a collection like List<String> or similar.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList())
        );
    }
}
