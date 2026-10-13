package post_service.post_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
public class CommentResponse {

    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private Instant createdAt;
}