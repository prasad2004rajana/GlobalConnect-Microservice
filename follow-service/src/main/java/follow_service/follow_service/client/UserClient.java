package follow_service.follow_service.client;

import follow_service.follow_service.dto.UserResponse;
import follow_service.follow_service.dto.UserSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-service", fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    @GetMapping("/api/auth/users/{id}")
    UserResponse getUser(@PathVariable("id") Long id);

    @GetMapping("/api/auth/users/batch")
    List<UserSummary> getUsers(@RequestParam("ids") List<Long> ids);
}