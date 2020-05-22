package com.example.demo.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	private UserDAOService service;
	
	//retrieveAllUsers
	@GetMapping(path = "/users")
	public List<User> getAllUsers() {
		return service.findAll();
	}
	
	//retrieve one user
	@GetMapping(path = "/users/{id}")
	public User getUser(@PathVariable Integer id) {
		return service.findOne(id);
	}
	
	//create a user
	@PostMapping(path = "/users")
	public User createUser(@RequestBody User user) {
		User savedUser = service.saveUser(user);
		return savedUser;
	}
}
