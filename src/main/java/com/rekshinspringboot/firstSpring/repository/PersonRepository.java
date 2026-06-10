package com.rekshinspringboot.firstSpring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rekshinspringboot.firstSpring.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	Optional<Person> findByUsername(String username);
}
