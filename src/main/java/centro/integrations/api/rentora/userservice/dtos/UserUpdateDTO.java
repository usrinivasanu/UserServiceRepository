package centro.integrations.api.rentora.userservice.dtos;

import java.time.LocalDate;

import centro.integrations.api.rentora.userservice.entities.UserType;
import lombok.Data;

@Data
public class UserUpdateDTO {
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private UserType usertype; // Assuming UserType is an enum
}
