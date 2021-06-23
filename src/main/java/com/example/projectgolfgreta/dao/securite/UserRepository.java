package com.example.projectgolfgreta.dao.securite;

import com.example.projectgolfgreta.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String name);
}
