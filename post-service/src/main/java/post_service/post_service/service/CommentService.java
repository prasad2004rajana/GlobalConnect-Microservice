package post_service.post_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import post_service.post_service.dto.CommentRequest;
import post_service.post_service.dto.CommentResponse;
import post_service.post_service.dto.PageResponse;
import post_service.post_service.entity.CommentEntity;
import post_service.post_service.entity.PostEntity;
import post_service.post_service.exception.ForbiddenException;
import post_service.post_service.exception.ResourceNotFoundException;
import post_service.post_service.mapper.PostMapper;
import post_service.post_service.repository.CommentRepository;
import post_service.post_service.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional
    public CommentResponse addComment(Long postId, CommentRequest request, Long userId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("post not found");
        }
        CommentEntity comment = new CommentEntity();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setContent(request.getContent().trim());
        CommentEntity saved = commentRepository.save(comment);
        postRepository.incrementCommentCount(postId);
        return postMapper.toCommentResponse(saved);
    }

    public PageResponse<CommentResponse> getComments(Long postId, int page, int size) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("post not found");
        }
        Page<CommentEntity> comments =
                commentRepository.findByPostIdOrderByCreatedAtAscIdAsc(postId, PostService.pageable(page, size));
        return PageResponse.from(comments, comments.getContent().stream().map(postMapper::toCommentResponse).toList());
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, Long userId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .filter(c -> c.getPostId().equals(postId))
                .orElseThrow(() -> new ResourceNotFoundException("comment not found"));
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post not found"));

        if (!comment.getUserId().equals(userId) && !post.getUserId().equals(userId)) {
            throw new ForbiddenException("you cannot delete this comment");
        }
        commentRepository.delete(comment);
        postRepository.decrementCommentCount(postId);
    }
}