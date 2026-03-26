package com.rekshinspringboot.firstSpring.controller;


import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rekshinspringboot.firstSpring.entity.User;
import com.rekshinspringboot.firstSpring.service.UserService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class userController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User createUser = userService.createUser(user);
		return new ResponseEntity<User>(createUser, HttpStatus.CREATED);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<User> setUserById(@PathVariable("id") int id){
		User getUser = userService.getUserDataById(id);
		return new ResponseEntity<>(getUser, HttpStatus.OK);
	}
	
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allData = userService.allUser();
		return new ResponseEntity<>(allData, HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<User> updateuser(@PathVariable("id") int id, @RequestBody User user){
		user.setId(id);
		User updatedata = userService.userUpdate(user);
		return new ResponseEntity<>(updatedata, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") int userId){
		userService.deleteUserById(userId);
		return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
	}
}
 