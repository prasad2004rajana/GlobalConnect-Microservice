package post_service.post_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import post_service.post_service.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findByPostIdOrderByCreatedAtAscIdAsc(Long postId, Pageable pageable);

    void deleteByPostId(Long postId);
}