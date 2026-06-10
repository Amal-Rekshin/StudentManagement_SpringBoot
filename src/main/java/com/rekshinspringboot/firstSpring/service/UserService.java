package com.rekshinspringboot.firstSpring.service;

import java.util.List;

import com.rekshinspringboot.firstSpring.entity.User;

public interface UserService {
	
	User createUser(User user);
	
	User getUserDataById(int id);
	
	User userUpdate(User user);
	
	List<User> allUser();
	
	void deleteUserById(int id);
}
