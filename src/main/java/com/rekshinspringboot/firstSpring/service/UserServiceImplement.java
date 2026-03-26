package com.rekshinspringboot.firstSpring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rekshinspringboot.firstSpring.entity.User;
import com.rekshinspringboot.firstSpring.repository.UserRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UserServiceImplement implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User createUser(User user) {
		// TODO Auto-generated method stub
		
		return userRepository.save(user);
	}

	@Override
	public User getUserDataById(int id) {
		// TODO Auto-generated method stub
		
		Optional<User> optionalUser = userRepository.findById(id); 
		return optionalUser.get();
	}

	@Override
	public User userUpdate(User user) {
		// TODO Auto-generated method stub
		
		User existUser = userRepository.findById(user.getId()).get();
		existUser.setMail(user.getMail());
		existUser.setCourse(user.getCourse());
		existUser.setName(user.getName());
		return userRepository.save(existUser);
	}

	@Override
	public List<User> allUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public void deleteUserById(int id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
		
		System.out.println("Delete user successfully");
	}

}
