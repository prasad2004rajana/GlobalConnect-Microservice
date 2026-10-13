package follow_service.follow_service.dto;

public record FollowStatsResponse(Long userId, long followerCount, long followingCount, boolean followedByMe) {
}