package user_service.user_service.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user_service.user_service.dto.*;
import user_service.user_service.service.LoginService;
import user_service.user_service.service.UserService;

import java.util.List;

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

    @GetMapping("/users/batch")
    public ResponseEntity<List<UserSummaryResponse>> getUsers(@RequestParam("ids") List<Long> ids) {
        return ResponseEntity.ok(userService.getUserSummaries(ids));
    }
    @GetMapping("/users/search")
    public ResponseEntity<List<UserSummaryResponse>> search(@RequestParam("q") String q) {
        return ResponseEntity.ok(userService.searchUsers(q));
    }
    @GetMapping("/users/recent")
    public ResponseEntity<List<UserSummaryResponse>> recentUsers() {
        return ResponseEntity.ok(userService.getRecentUsers());
    }
}
