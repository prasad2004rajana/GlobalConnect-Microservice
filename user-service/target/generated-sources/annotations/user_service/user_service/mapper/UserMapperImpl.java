package user_service.user_service.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import user_service.user_service.dto.LoginResponse;
import user_service.user_service.dto.UserRequest;
import user_service.user_service.dto.UserResponse;
import user_service.user_service.entity.UserEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-08T18:11:11+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.12 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toEntity(UserRequest request) {
        if ( request == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername( request.getUsername() );
        userEntity.setEmail( request.getEmail() );
        userEntity.setPassword( request.getPassword() );

        return userEntity;
    }

    @Override
    public UserResponse toResponse(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setId( entity.getId() );
        userResponse.setUsername( entity.getUsername() );
        userResponse.setEmail( entity.getEmail() );

        return userResponse;
    }

    @Override
    public LoginResponse toLoginResponse(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setId( entity.getId() );
        loginResponse.setUsername( entity.getUsername() );
        loginResponse.setEmail( entity.getEmail() );

        return loginResponse;
    }
}
