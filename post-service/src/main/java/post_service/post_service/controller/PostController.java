package post_service.post_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import post_service.post_service.dto.PageResponse;
import post_service.post_service.dto.PostRequest;
import post_service.post_service.dto.PostResponse;
import post_service.post_service.service.LikeService;
import post_service.post_service.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private static final String USER_ID = "X-User-Id";

    private final PostService postService;
    private final LikeService likeService;

    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@RequestHeader(USER_ID) Long userId,
                                                   @Valid @RequestBody PostRequest postRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postRequest, userId));
    }

    @GetMapping("/feed")
    public ResponseEntity<PageResponse<PostResponse>> feed(@RequestHeader(USER_ID) Long userId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.getFeed(userId, page, size));
    }

    @GetMapping("/user/{authorId}")
    public ResponseEntity<PageResponse<PostResponse>> postsByUser(@RequestHeader(USER_ID) Long userId,
                                                                  @PathVariable Long authorId,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.getPostsByUser(authorId, userId, page, size));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@RequestHeader(USER_ID) Long userId,
                                                @PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId, userId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@RequestHeader(USER_ID) Long userId,
                                           @PathVariable Long postId) {
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> like(@RequestHeader(USER_ID) Long userId, @PathVariable Long postId) {
        likeService.like(postId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<Void> unlike(@RequestHeader(USER_ID) Long userId, @PathVariable Long postId) {
        likeService.unlike(postId, userId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/recent")
    public ResponseEntity<PageResponse<PostResponse>> recent(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.getRecent(userId, page, size));
    }
}