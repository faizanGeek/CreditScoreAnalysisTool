package com.credit.userms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.credit.userms.dto.UserDTO;
import com.credit.userms.entity.User;
import com.credit.userms.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDTO registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return mapToDTO(user);
    }

    public UserDTO loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return mapToDTO(user);
        }
        return null;
    }

    private UserDTO mapToDTO(User user) {
    	return new UserDTO(user.getUsername(), user.getEmail(), user.getRole());
    }

	public UserDTO getUserDetails(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return new UserDTO(user.getUsername(), user.getEmail(), user.getRole());
        }
        return null;
	}
}

