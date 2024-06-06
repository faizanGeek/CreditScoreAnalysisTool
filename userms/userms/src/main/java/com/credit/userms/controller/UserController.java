package com.credit.userms.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.credit.userms.dto.UserDTO;
import com.credit.userms.entity.User;
import com.credit.userms.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user, HttpServletRequest request) {
        UserDTO userDTO = userService.registerUser(user);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        UserDTO user = userService.loginUser(username, password);
        if (user != null) {
            // Create a JWT token with configurations:
            String token = JWT.create()
                .withSubject(user.getUsername())  // Set the 'subject' claim as the username
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // Set expiration time to 10 minutes from now
                .withIssuer(request.getRequestURL().toString())  // Set the 'issuer' claim to the URL where the request was made
                .sign(Algorithm.HMAC256("secret")); // Sign the token with HMAC256 algorithm using the 'secret' key

            // Return the ResponseEntity with status OK, and add the token in the Authorization header
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).body(user);
        } else {
            // If the user is not found or the authentication failed, return an unauthorized status
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<String> getUserById(@PathVariable Long userId) {
        UserDTO userDTO = userService.getUserDetails(userId);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO.getEmail()); // Credit Scoring Service is getting this emailId
        }
        return ResponseEntity.notFound().build();
    }
}

