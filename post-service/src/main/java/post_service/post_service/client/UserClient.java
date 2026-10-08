package post_service.post_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import post_service.post_service.dto.UserResponse;

@FeignClient(name = "user-service", fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    @GetMapping("/api/auth/users/{id}")
    UserResponse getUser(@PathVariable("id") Long id);

}


