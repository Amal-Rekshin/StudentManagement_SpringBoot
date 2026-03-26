package com.rekshinspringboot.firstSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rekshinspringboot.firstSpring.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
