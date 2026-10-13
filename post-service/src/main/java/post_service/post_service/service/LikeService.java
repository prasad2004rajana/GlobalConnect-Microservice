package post_service.post_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import post_service.post_service.entity.LikeEntity;
import post_service.post_service.exception.ResourceNotFoundException;
import post_service.post_service.repository.LikeRepository;
import post_service.post_service.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Transactional
    public void like(Long postId, Long userId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("post not found");
        }
        if (likeRepository.existsByPostIdAndUserId(postId, userId)) {
            return;
        }
        LikeEntity like = new LikeEntity();
        like.setPostId(postId);
        like.setUserId(userId);
        likeRepository.save(like);
        postRepository.incrementLikeCount(postId);
    }

    @Transactional
    public void unlike(Long postId, Long userId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("post not found");
        }
        if (likeRepository.deleteByPostIdAndUserId(postId, userId) > 0) {
            postRepository.decrementLikeCount(postId);
        }
    }
}