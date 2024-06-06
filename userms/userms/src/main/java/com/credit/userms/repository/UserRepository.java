package com.credit.userms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credit.userms.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

