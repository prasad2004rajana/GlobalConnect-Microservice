package user_service.user_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import user_service.user_service.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity>findByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
    List<UserEntity> findTop20ByUsernameContainingIgnoreCase(String username);
    List<UserEntity> findTop20ByOrderByIdDesc();
}
