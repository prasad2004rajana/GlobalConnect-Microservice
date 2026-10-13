package post_service.post_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import post_service.post_service.entity.PostEntity;

import java.util.Collection;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findByUserIdOrderByCreatedAtDescIdDesc(Long userId, Pageable pageable);

    Page<PostEntity> findByUserIdInOrderByCreatedAtDescIdDesc(Collection<Long> userIds, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update PostEntity p set p.likeCount = p.likeCount + 1 where p.id = :id")
    void incrementLikeCount(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update PostEntity p set p.likeCount = p.likeCount - 1 where p.id = :id and p.likeCount > 0")
    void decrementLikeCount(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update PostEntity p set p.commentCount = p.commentCount + 1 where p.id = :id")
    void incrementCommentCount(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update PostEntity p set p.commentCount = p.commentCount - 1 where p.id = :id and p.commentCount > 0")
    void decrementCommentCount(@Param("id") Long id);
}