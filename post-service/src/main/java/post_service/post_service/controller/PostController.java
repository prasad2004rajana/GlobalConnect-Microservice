package post_service.post_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import post_service.post_service.dto.PostRequest;
import post_service.post_service.dto.PostResponse;

import post_service.post_service.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest){
        PostResponse postResponse= postService.createPost(postRequest);
        return ResponseEntity.ok(postResponse);

    }
}
