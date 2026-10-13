package post_service.post_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

    private Long id;
    private Long userId;
    private String content;
    private Instant createdAt;
    private long likeCount;
    private long commentCount;
    private boolean likedByMe;
}