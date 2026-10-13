package post_service.post_service.client;

import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import post_service.post_service.dto.UserResponse;
import post_service.post_service.exception.ServiceUnavailableException;

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
        };
    }
}