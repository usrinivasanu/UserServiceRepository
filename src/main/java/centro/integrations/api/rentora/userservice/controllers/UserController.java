package centro.integrations.api.rentora.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centro.integrations.api.rentora.userservice.entities.User;
import centro.integrations.api.rentora.userservice.exceptions.RegisterUserFailedException;
import centro.integrations.api.rentora.userservice.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody @Validated User user) throws RegisterUserFailedException {
		try {
			User registeredUser = userService.registerUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
		} catch (IllegalArgumentException e) {
			// Invalid property data
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			// Unexpected error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
		}

	}

	@GetMapping("/user/{username}")
	public ResponseEntity<?> getUser(@PathVariable String username) {
		try {
			User user = userService.getUserByUsername(username);
			if (user != null) {
				return ResponseEntity.ok(user);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("User with username " + username + " not found.");
			}
		} catch (IllegalArgumentException e) {
			// Invalid ID format or other argument issues
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username provided.");
		} catch (Exception e) {
			// General error handling
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
		}
	}

}
