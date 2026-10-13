package post_service.post_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentRequest {

    @NotBlank
    @Size(max = 500, message = "comment must be at most 500 characters")
    private String content;
}