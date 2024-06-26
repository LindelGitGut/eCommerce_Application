package com.example.demo.controllers;

import com.example.demo.Service.UserService;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class.getSimpleName());

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		return ResponseEntity.of(userRepository.findById(id));
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);

		if (!createUserRequest.getPassword().equals( createUserRequest.getConfirmPassword())){
			logger.warn("Create User " +createUserRequest.getUsername() +" failed, Password and confirmed Password are not equal!");
			return ResponseEntity.badRequest().build();
			}
		else if(createUserRequest.getPassword() == null || createUserRequest.getPassword().length() < 7){
			logger.warn("Create User " +createUserRequest.getUsername() +" failed. Password must have at least a length of 7");
			return ResponseEntity.badRequest().build();}

			//Encode Password before saving
			user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
			userService.saveUser(user);
			logger.info(""+createUserRequest.getUsername() + " created successfully. User ID: "+user.getId());
			return ResponseEntity.ok(user);}

}
