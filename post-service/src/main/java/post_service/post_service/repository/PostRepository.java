package post_service.post_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import post_service.post_service.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

}
