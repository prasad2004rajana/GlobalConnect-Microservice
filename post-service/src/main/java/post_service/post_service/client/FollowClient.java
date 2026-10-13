package post_service.post_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "follow-service", fallbackFactory = FollowClientFallbackFactory.class)
public interface FollowClient {

    @GetMapping("/api/follows/{userId}/following-ids")
    List<Long> getFollowingIds(@PathVariable("userId") Long userId);
}