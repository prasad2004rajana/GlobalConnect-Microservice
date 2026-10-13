package follow_service.follow_service.service;

import follow_service.follow_service.client.UserClient;
import follow_service.follow_service.dto.FollowStatsResponse;
import follow_service.follow_service.dto.PageResponse;
import follow_service.follow_service.dto.UserSummary;
import follow_service.follow_service.entity.FollowEntity;
import follow_service.follow_service.exception.BadRequestException;
import follow_service.follow_service.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private static final int MAX_PAGE_SIZE = 50;
    private static final int MAX_FOLLOWING_FOR_FEED = 1000;

    private final FollowRepository followRepository;
    private final UserClient userClient;

    public void follow(Long currentUserId, Long targetUserId) {
        if (currentUserId.equals(targetUserId)) {
            throw new BadRequestException("you cannot follow yourself");
        }
        userClient.getUser(targetUserId);

        if (followRepository.existsByFollowerIdAndFollowingId(currentUserId, targetUserId)) {
            return;
        }
        FollowEntity follow = new FollowEntity();
        follow.setFollowerId(currentUserId);
        follow.setFollowingId(targetUserId);
        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(Long currentUserId, Long targetUserId) {
        followRepository.deleteByFollowerIdAndFollowingId(currentUserId, targetUserId);
    }

    public PageResponse<UserSummary> getFollowers(Long userId, int page, int size) {
        Page<FollowEntity> result = followRepository.findByFollowingIdOrderByCreatedAtDescIdDesc(userId, pageable(page, size));
        List<Long> ids = result.getContent().stream().map(FollowEntity::getFollowerId).toList();
        return PageResponse.from(result, resolveUsers(ids));
    }

    public PageResponse<UserSummary> getFollowing(Long userId, int page, int size) {
        Page<FollowEntity> result = followRepository.findByFollowerIdOrderByCreatedAtDescIdDesc(userId, pageable(page, size));
        List<Long> ids = result.getContent().stream().map(FollowEntity::getFollowingId).toList();
        return PageResponse.from(result, resolveUsers(ids));
    }

    public FollowStatsResponse getStats(Long userId, Long currentUserId) {
        return new FollowStatsResponse(
                userId,
                followRepository.countByFollowingId(userId),
                followRepository.countByFollowerId(userId),
                followRepository.existsByFollowerIdAndFollowingId(currentUserId, userId));
    }

    public List<Long> getFollowingIds(Long userId) {
        return followRepository.findFollowingIds(userId, PageRequest.of(0, MAX_FOLLOWING_FOR_FEED));
    }

    private List<UserSummary> resolveUsers(Collection<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        Map<Long, UserSummary> byId = userClient.getUsers(List.copyOf(ids)).stream()
                .collect(Collectors.toMap(UserSummary::id, Function.identity()));
        return ids.stream().map(byId::get).filter(u -> u != null).toList();
    }

    private static Pageable pageable(int page, int size) {
        return PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), MAX_PAGE_SIZE));
    }
}