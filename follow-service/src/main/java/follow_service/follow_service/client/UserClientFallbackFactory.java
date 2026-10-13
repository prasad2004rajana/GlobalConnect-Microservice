package follow_service.follow_service.client;

import feign.FeignException;
import follow_service.follow_service.dto.UserResponse;
import follow_service.follow_service.dto.UserSummary;
import follow_service.follow_service.exception.ServiceUnavailableException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public UserResponse getUser(Long id) {
                if (cause instanceof FeignException.NotFound notFound) {
                    throw notFound;
                }
                throw new ServiceUnavailableException("user-service unavailable, try again shortly");
            }

            @Override
            public List<UserSummary> getUsers(List<Long> ids) {
                throw new ServiceUnavailableException("user-service unavailable, try again shortly");
            }
        };
    }
}