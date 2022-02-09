package com.dagurasu.rest.webservices.restfulwebservices.user;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dagurasu.rest.webservices.restfulwebservices.exception.UserNotFoundException;

@RestController
public class UseJPAResource {

	@Autowired
	private UserDaoService service;
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		
		if (!user.isPresent())
			throw new UserNotFoundException("id-" + id);
		
		EntityModel<User> model = EntityModel.of(user.get());
		
		WebMvcLinkBuilder linkToUsers = 
				linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		model.add(linkToUsers.withRel("all-users"));
		
		return model;
	}

	@PostMapping("/jpa/users")
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		return savedUser;
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int id) {
		User user = service.deleteById(id);
		
		if (user == null)
			throw new UserNotFoundException("id-" + id);
		
		return ResponseEntity.noContent().build();
	}

}
