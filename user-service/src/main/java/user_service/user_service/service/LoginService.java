package user_service.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user_service.user_service.dto.LoginRequest;
import user_service.user_service.dto.LoginResponse;
import user_service.user_service.entity.UserEntity;
import user_service.user_service.mapper.UserMapper;
import user_service.user_service.repository.UserRepository;
import user_service.user_service.security.JwtService;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public LoginResponse login(LoginRequest loginRequest){
        UserEntity user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new RuntimeException("user not found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("password or mail mismatches");
        }
        LoginResponse response=userMapper.toLoginResponse(user);

        String token=jwtService.generateToken(user.getEmail());
        response.setToken(token);
        return response;
    }
}
