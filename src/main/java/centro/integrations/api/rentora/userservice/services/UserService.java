package centro.integrations.api.rentora.userservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import centro.integrations.api.rentora.userservice.dtos.UserUpdateDTO;
import centro.integrations.api.rentora.userservice.entities.User;
import centro.integrations.api.rentora.userservice.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User registerUser(User user) {
		// Additional logic such as password encryption, validation, etc. can be added
		// here
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);

	}

	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User updateUser(String username, UserUpdateDTO userUpdateDTO) {
		try {
			User existingUser = userRepository.findByUsername(username);
			if (existingUser == null) {
				throw (new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
			} else {
				if (userUpdateDTO.getUsername() != null) {
					existingUser.setUsername(userUpdateDTO.getUsername());
				}
				if (userUpdateDTO.getPassword() != null) {
					existingUser.setPassword(userUpdateDTO.getPassword());
				}
				if (userUpdateDTO.getEmail() != null) {
					existingUser.setEmail(userUpdateDTO.getEmail());
				}
				if (userUpdateDTO.getFirstName() != null) {
					existingUser.setFirstName(userUpdateDTO.getFirstName());
				}
				if (userUpdateDTO.getLastName() != null) {
					existingUser.setLastName(userUpdateDTO.getLastName());
				}
				if (userUpdateDTO.getDateOfBirth() != null) {
					existingUser.setDateOfBirth(userUpdateDTO.getDateOfBirth());
				}
				if (userUpdateDTO.getUsertype() != null) {
					existingUser.setUsertype(userUpdateDTO.getUsertype());
				}

				return userRepository.save(existingUser);
			}
		}

		catch (Exception e) {
			throw (new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"An unexpected error occurred: \" + e.getMessage()"));

		}

	}

}
