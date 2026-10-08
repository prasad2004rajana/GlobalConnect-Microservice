package post_service.post_service.service;


import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import post_service.post_service.client.UserClient;
import post_service.post_service.dto.PostRequest;
import post_service.post_service.dto.PostResponse;
import post_service.post_service.entity.PostEntity;
import post_service.post_service.mapper.PostMapper;
import post_service.post_service.repository.PostRepository;

@Service
@RequiredArgsConstructor

public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserClient userClient;

    public PostResponse createPost(PostRequest postRequest){

        userClient.getUser(postRequest.getUserId());

        PostEntity post = postMapper.toEntity(postRequest);
        return postMapper.toResponse(postRepository.save(post));
    }

}
