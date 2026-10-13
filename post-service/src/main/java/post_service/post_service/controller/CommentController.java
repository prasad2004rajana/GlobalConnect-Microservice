package post_service.post_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import post_service.post_service.dto.CommentRequest;
import post_service.post_service.dto.CommentResponse;
import post_service.post_service.dto.PageResponse;
import post_service.post_service.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private static final String USER_ID = "X-User-Id";

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> add(@RequestHeader(USER_ID) Long userId,
                                               @PathVariable Long postId,
                                               @Valid @RequestBody CommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(postId, request, userId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<CommentResponse>> list(@PathVariable Long postId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(commentService.getComments(postId, page, size));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@RequestHeader(USER_ID) Long userId,
                                       @PathVariable Long postId,
                                       @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId, userId);
        return ResponseEntity.noContent().build();
    }
}