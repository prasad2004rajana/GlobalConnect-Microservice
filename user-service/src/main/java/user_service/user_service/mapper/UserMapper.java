package user_service.user_service.mapper;


import org.mapstruct.Mapper;
import user_service.user_service.dto.LoginResponse;
import user_service.user_service.dto.UserRequest;
import user_service.user_service.dto.UserResponse;
import user_service.user_service.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(UserRequest request);
    UserResponse toResponse(UserEntity entity);
    LoginResponse toLoginResponse(UserEntity entity);
}
