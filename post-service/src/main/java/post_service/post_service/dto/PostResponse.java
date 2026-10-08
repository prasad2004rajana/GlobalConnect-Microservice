package post_service.post_service.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

    private Long id;

    private String content;
}
