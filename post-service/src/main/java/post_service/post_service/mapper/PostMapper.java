package post_service.post_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import post_service.post_service.dto.CommentResponse;
import post_service.post_service.dto.PostResponse;
import post_service.post_service.entity.CommentEntity;
import post_service.post_service.entity.PostEntity;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "likedByMe", ignore = true)
    PostResponse toResponse(PostEntity post);

    CommentResponse toCommentResponse(CommentEntity comment);
}