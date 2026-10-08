package user_service.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "username is required")
    @Size(min = 3, max = 20, message = "username must be between 2 and 20")
    private String username;
    @NotBlank(message = "email is required")
    @Email(message = "invalid email format")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 5, message = "password must be at least 5 characters")
    private String password;

}
