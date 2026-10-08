package user_service.user_service.service;



import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import user_service.user_service.dto.UserRequest;
import user_service.user_service.dto.UserResponse;
import user_service.user_service.entity.UserEntity;
import user_service.user_service.mapper.UserMapper;
import user_service.user_service.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register( UserRequest request){
        UserEntity user= userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse getUserById(Long id) {

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toResponse(user);
    }
}

