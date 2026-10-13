package follow_service.follow_service.repository;

import follow_service.follow_service.entity.FollowEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    long deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

    long countByFollowerId(Long followerId);

    long countByFollowingId(Long followingId);

    Page<FollowEntity> findByFollowingIdOrderByCreatedAtDescIdDesc(Long followingId, Pageable pageable);

    Page<FollowEntity> findByFollowerIdOrderByCreatedAtDescIdDesc(Long followerId, Pageable pageable);

    @Query("select f.followingId from FollowEntity f where f.followerId = :followerId order by f.createdAt desc")
    List<Long> findFollowingIds(@Param("followerId") Long followerId, Pageable pageable);
}