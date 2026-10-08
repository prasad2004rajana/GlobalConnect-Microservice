package user_service.user_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {

    private Long id;
    private String username;
    private String email;
    private String token;

}
