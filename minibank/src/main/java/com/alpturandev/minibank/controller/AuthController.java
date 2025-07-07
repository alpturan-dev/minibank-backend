package com.alpturandev.minibank.controller;

import com.alpturandev.minibank.dto.SigninRequestDto;
import com.alpturandev.minibank.dto.SigninResponseDto;
import com.alpturandev.minibank.dto.SignupRequestDto;
import com.alpturandev.minibank.entity.User;
import com.alpturandev.minibank.repository.UserRepository;
import com.alpturandev.minibank.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<SigninResponseDto> authenticateUser(@RequestBody SigninRequestDto user) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
            )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token =  jwtUtils.generateToken(userDetails.getUsername());

        // Load the user entity from DB
        User userEntity = userRepository.findByUsername(userDetails.getUsername());

        SigninResponseDto response = new SigninResponseDto(
            userEntity.getId(),
            token,
            userEntity.getUsername(),
            userEntity.getEmail()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public String registerUser(@RequestBody SignupRequestDto user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Error: Username is already taken!";
        }
        // Create new user's account
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        userRepository.save(newUser);
        return "User registered successfully!";
    }
}
