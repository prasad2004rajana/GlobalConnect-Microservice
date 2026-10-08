package user_service.user_service.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user_service.user_service.dto.LoginRequest;
import user_service.user_service.dto.LoginResponse;
import user_service.user_service.dto.UserRequest;
import user_service.user_service.dto.UserResponse;
import user_service.user_service.service.LoginService;
import user_service.user_service.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest){
        UserResponse response= userService.register(userRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {

        UserResponse response = userService.getUserById(id);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        LoginResponse response=loginService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
