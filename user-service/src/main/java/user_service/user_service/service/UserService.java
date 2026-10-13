package user_service.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user_service.user_service.dto.UserRequest;
import user_service.user_service.dto.UserResponse;
import user_service.user_service.dto.UserSummaryResponse;
import user_service.user_service.entity.UserEntity;
import user_service.user_service.exception.ConflictException;
import user_service.user_service.exception.ResourceNotFoundException;
import user_service.user_service.mapper.UserMapper;
import user_service.user_service.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("email already registered");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("username already taken");
        }
        UserEntity user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        return userMapper.toResponse(user);
    }

    public List<UserSummaryResponse> getUserSummaries(List<Long> ids) {
        List<Long> limited = ids.stream().distinct().limit(100).toList();
        return userRepository.findAllById(limited).stream()
                .map(u -> new UserSummaryResponse(u.getId(), u.getUsername()))
                .toList();
    }
    public List<UserSummaryResponse> searchUsers(String query) {
        String q = query == null ? "" : query.trim();
        if (q.length() < 2) {
            return List.of();
        }
        return userRepository.findTop20ByUsernameContainingIgnoreCase(q).stream()
                .map(u -> new UserSummaryResponse(u.getId(), u.getUsername()))
                .toList();
    }
    public List<UserSummaryResponse> getRecentUsers() {
        return userRepository.findTop20ByOrderByIdDesc().stream()
                .map(u -> new UserSummaryResponse(u.getId(), u.getUsername()))
                .toList();
    }
}