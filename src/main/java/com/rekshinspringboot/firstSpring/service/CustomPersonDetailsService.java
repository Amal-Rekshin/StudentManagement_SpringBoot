package com.rekshinspringboot.firstSpring.service;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rekshinspringboot.firstSpring.entity.Person;
import com.rekshinspringboot.firstSpring.repository.PersonRepository;


@Service
public class CustomPersonDetailsService implements UserDetailsService {
	
	private final PersonRepository personRepository;
	
	public CustomPersonDetailsService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Person person = personRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found" + username));
		
		return new org.springframework.security.core.userdetails.User(person.getUsername(), person.getPassword(), person.getRoles().stream()
				.map(role ->  new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
	}

}
