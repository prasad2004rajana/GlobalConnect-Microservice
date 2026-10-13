package post_service.post_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import post_service.post_service.entity.LikeEntity;

import java.util.Collection;
import java.util.List;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    long deleteByPostIdAndUserId(Long postId, Long userId);

    void deleteByPostId(Long postId);

    @Query("select l.postId from LikeEntity l where l.userId = :userId and l.postId in :postIds")
    List<Long> findLikedPostIds(@Param("userId") Long userId, @Param("postIds") Collection<Long> postIds);
}