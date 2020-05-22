package com.example.demo.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
	
	@Autowired
	private UserDAOService service;
	
	//retrieveAllUsers
	@GetMapping(path = "/users")
	public List<User> getAllUsers() {
		List<User> users = service.findAll();
		if (users.size() == 0) throw new UsersNotFoundException("No users created");
		return users;
	}
	
	//retrieve one user
	@GetMapping(path = "/users/{id}")
	public User getUser(@PathVariable Integer id) {
		User user = service.findOne(id);
		if (user == null) throw new UserNotFoundException(String.format("id = %s", id));
		return user;
	}
	
	//create a user
	@PostMapping(path = "/users")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		User savedUser = service.saveUser(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build(); //goes in the header
	}
	
	//delete a user
	//for this, we can either return a no-content ResponseEntity, or return void to signify success at deleting the resource.
	@DeleteMapping(path = "/users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int id) {
		User user = service.deleteOne(id);
		if (user == null) throw new UserNotFoundException("id = " + id);
		return ResponseEntity.noContent().build();
	}
}
