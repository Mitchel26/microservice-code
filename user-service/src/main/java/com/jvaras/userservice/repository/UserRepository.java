package com.jvaras.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jvaras.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
