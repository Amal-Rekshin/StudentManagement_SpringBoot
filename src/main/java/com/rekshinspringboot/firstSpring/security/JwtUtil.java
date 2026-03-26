package com.rekshinspringboot.firstSpring.security;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.rekshinspringboot.firstSpring.entity.Person;
import com.rekshinspringboot.firstSpring.entity.Role;
import com.rekshinspringboot.firstSpring.repository.PersonRepository;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	
//	Secrest key
	private static final SecretKey secretkey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
	
	
	
	
//	Expire Time
	private static int keyExpirationMs = 86400000;
	
	private PersonRepository personRepository;
	
	private JwtUtil(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	
	// Generate Token
	
	public String generateToken(String username) {
		Optional<Person> person = personRepository.findByUsername(username);
		Set<Role> roles = person.get().getRoles();
		
		return Jwts.builder()
				.setSubject(username)
				.claim("roles", roles.stream().map(role -> role.getName()).collect(Collectors.joining(",")))
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + keyExpirationMs))
				.signWith(secretkey)
				.compact();
	}
	
	public String extractUsername(String token) {
		return Jwts.parser()
				.setSigningKey(secretkey)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	
	public Set<String> extractRole(String token) {
		String rolesString = Jwts.parser().setSigningKey(secretkey).build().parseClaimsJws(token).getBody().get("roles", String.class);
		return Set.of(rolesString);
	}
	
	// Token Validation
	public boolean isValidToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretkey).build().parseClaimsJws(token);
			return true;
		}
		catch(JwtException | IllegalArgumentException e) {
			return false;
		}
	}
	
	
}
