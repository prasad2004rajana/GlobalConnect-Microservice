package post_service.post_service.mapper;

import org.mapstruct.Mapper;
import post_service.post_service.dto.PostRequest;
import post_service.post_service.dto.PostResponse;
import post_service.post_service.entity.PostEntity;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostEntity  toEntity(PostRequest request);
    PostResponse toResponse(PostEntity postentity);

}
