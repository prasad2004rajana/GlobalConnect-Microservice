package user_service.user_service.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import user_service.user_service.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity>findByEmail(String email);
}
