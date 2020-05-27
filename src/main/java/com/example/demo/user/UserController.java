package com.example.demo.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/users")
public class UserController {
	
	@Autowired
	private UserDAOService service;
	
	//retrieveAllUsers
	@GetMapping(path = "")
	public CollectionModel<User> getAllUsers() {
		List<User> users = service.findAll();
		if (users.size() == 0) throw new UsersNotFoundException("No users created");
		for (User user : users) {
			EntityModel<User> model = new EntityModel<>(user);
			user.add(linkTo(UserController.class).slash(user.getId()).withSelfRel());
		}
		Link selfLink = linkTo(UserController.class).withSelfRel();
		CollectionModel<User> result = new CollectionModel<>(users, selfLink);
		return result;
	}
	
	//retrieve one user
	@GetMapping(path = "/{id}")
	public EntityModel<User> getUser(@PathVariable Integer id) {
		User user = service.findOne(id);
		if (user == null) throw new UserNotFoundException(String.format("id = %s", id));
		
//		Link link = new Link("http://localhost:8080/users/1"); -> hard coding links
//		The WebMvcLinkBuilder class methods are imported statically
		
		EntityModel<User> model = new EntityModel<>(user);
		Link selfLink = linkTo(UserController.class).slash(user.getId()).withSelfRel(); 
		Link allUsers = linkTo(methodOn(UserController.class).getAllUsers()).withRel("all users");
		
		model.add(selfLink);
		model.add(allUsers);
		
		return model;
	}
	
	//create a user
	@PostMapping(path = "")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.saveUser(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build(); //goes in the header, returns the status code CREATED
	}
	
	//delete a user
	//for this, we can either return a no-content ResponseEntity, or return void to signify success at deleting the resource.
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int id) {
		User user = service.deleteOne(id);
		if (user == null) throw new UserNotFoundException("id = " + id);
		return ResponseEntity.noContent().build(); //return status code NO_CONTENT
	}
}
