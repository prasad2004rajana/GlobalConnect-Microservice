package follow_service.follow_service.controller;

import follow_service.follow_service.dto.FollowStatsResponse;
import follow_service.follow_service.dto.PageResponse;
import follow_service.follow_service.dto.UserSummary;
import follow_service.follow_service.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
public class FollowController {

    private static final String USER_ID = "X-User-Id";

    private final FollowService followService;

    @PostMapping("/{targetUserId}")
    public ResponseEntity<Void> follow(@RequestHeader(USER_ID) Long userId, @PathVariable Long targetUserId) {
        followService.follow(userId, targetUserId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{targetUserId}")
    public ResponseEntity<Void> unfollow(@RequestHeader(USER_ID) Long userId, @PathVariable Long targetUserId) {
        followService.unfollow(userId, targetUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<PageResponse<UserSummary>> followers(@PathVariable Long userId,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(followService.getFollowers(userId, page, size));
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<PageResponse<UserSummary>> following(@PathVariable Long userId,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(followService.getFollowing(userId, page, size));
    }

    @GetMapping("/{userId}/stats")
    public ResponseEntity<FollowStatsResponse> stats(@RequestHeader(USER_ID) Long currentUserId,
                                                     @PathVariable Long userId) {
        return ResponseEntity.ok(followService.getStats(userId, currentUserId));
    }

    @GetMapping("/{userId}/following-ids")
    public ResponseEntity<List<Long>> followingIds(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowingIds(userId));
    }
}