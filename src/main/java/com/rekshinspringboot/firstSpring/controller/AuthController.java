package com.rekshinspringboot.firstSpring.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rekshinspringboot.firstSpring.dto.RegisterRequest;
import com.rekshinspringboot.firstSpring.entity.Person;
import com.rekshinspringboot.firstSpring.entity.Role;
import com.rekshinspringboot.firstSpring.repository.PersonRepository;
import com.rekshinspringboot.firstSpring.repository.RoleRepository;
import com.rekshinspringboot.firstSpring.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final PersonRepository personRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
			PersonRepository personRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.personRepository = personRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
		if(personRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
			return ResponseEntity.badRequest().body("User Already Taken");
		}
		
		Person person = new Person();
		person.setUsername(registerRequest.getUsername());
		
		String encodePassword = passwordEncoder.encode(registerRequest.getPassword());
		person.setPassword(encodePassword);
		System.out.println("Encode Password: " + encodePassword);
		
		Set<Role> roles = new HashSet<>();
		for(String roleName : registerRequest.getRoles()) {
			Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("User Role Not found: " + roleName));
			roles.add(role);
		}	
		person.setRoles(roles);
	
		return ResponseEntity.ok("User Register Successfully");
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Person loginRequest){
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		}
		catch(Exception e) {
			System.out.println("Exception: "+e);
		}
		
		String token = jwtUtil.generateToken(loginRequest.getUsername());
		return ResponseEntity.ok(token);
	}
	
}





















