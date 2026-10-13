package post_service.post_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostRequest {

    @NotBlank
    @Size(max = 1000, message = "post must be at most 1000 characters")
    private String content;
}