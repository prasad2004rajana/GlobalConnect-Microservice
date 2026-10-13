package post_service.post_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import post_service.post_service.client.FollowClient;
import post_service.post_service.client.UserClient;
import post_service.post_service.dto.PageResponse;
import post_service.post_service.dto.PostRequest;
import post_service.post_service.dto.PostResponse;
import post_service.post_service.entity.PostEntity;
import post_service.post_service.exception.ForbiddenException;
import post_service.post_service.exception.ResourceNotFoundException;
import post_service.post_service.mapper.PostMapper;
import post_service.post_service.repository.CommentRepository;
import post_service.post_service.repository.LikeRepository;
import post_service.post_service.repository.PostRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService {

    private static final int MAX_PAGE_SIZE = 50;

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PostMapper postMapper;
    private final UserClient userClient;
    private final FollowClient followClient;

    public PostResponse createPost(PostRequest request, Long currentUserId) {
        userClient.getUser(currentUserId);

        PostEntity post = new PostEntity();
        post.setContent(request.getContent().trim());
        post.setUserId(currentUserId);

        PostResponse response = postMapper.toResponse(postRepository.save(post));
        response.setLikedByMe(false);
        return response;
    }

    public PostResponse getPost(Long postId, Long currentUserId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post not found"));
        return toResponses(List.of(post), currentUserId).get(0);
    }

    @Transactional
    public void deletePost(Long postId, Long currentUserId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post not found"));
        if (!post.getUserId().equals(currentUserId)) {
            throw new ForbiddenException("you can only delete your own posts");
        }
        likeRepository.deleteByPostId(postId);
        commentRepository.deleteByPostId(postId);
        postRepository.delete(post);
    }

    public PageResponse<PostResponse> getPostsByUser(Long userId, Long currentUserId, int page, int size) {
        Page<PostEntity> posts = postRepository.findByUserIdOrderByCreatedAtDescIdDesc(userId, pageable(page, size));
        return PageResponse.from(posts, toResponses(posts.getContent(), currentUserId));
    }

    public PageResponse<PostResponse> getFeed(Long currentUserId, int page, int size) {
        Set<Long> authors = new HashSet<>(followClient.getFollowingIds(currentUserId));
        authors.add(currentUserId);

        Page<PostEntity> posts = postRepository.findByUserIdInOrderByCreatedAtDescIdDesc(authors, pageable(page, size));
        return PageResponse.from(posts, toResponses(posts.getContent(), currentUserId));
    }

    private List<PostResponse> toResponses(List<PostEntity> posts, Long currentUserId) {
        if (posts.isEmpty()) {
            return List.of();
        }
        List<Long> ids = posts.stream().map(PostEntity::getId).toList();
        Set<Long> liked = currentUserId == null
                ? Set.of()
                : new HashSet<>(likeRepository.findLikedPostIds(currentUserId, ids));
        List<PostResponse> result = new ArrayList<>(posts.size());
        for (PostEntity post : posts) {
            PostResponse r = postMapper.toResponse(post);
            r.setLikedByMe(liked.contains(post.getId()));
            result.add(r);
        }
        return result;
    }
    public PageResponse<PostResponse> getRecent(Long currentUserId, int page, int size) {
        Pageable p = pageable(page, size);
        Pageable sorted = PageRequest.of(p.getPageNumber(), p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));
        Page<PostEntity> posts = postRepository.findAll(sorted);
        return PageResponse.from(posts, toResponses(posts.getContent(), currentUserId));
    }

    static Pageable pageable(int page, int size) {
        return PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), MAX_PAGE_SIZE));
    }
}