package post_service.post_service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class FollowClientFallbackFactory implements FallbackFactory<FollowClient> {

    @Override
    public FollowClient create(Throwable cause) {
        return userId -> {
            log.warn("follow-service unavailable, feed falls back to own posts: {}", cause.toString());
            return List.of();
        };
    }
}