package post_service.post_service.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import post_service.post_service.dto.PostRequest;
import post_service.post_service.dto.PostResponse;
import post_service.post_service.entity.PostEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-08T18:11:17+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.12 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostEntity toEntity(PostRequest request) {
        if ( request == null ) {
            return null;
        }

        PostEntity postEntity = new PostEntity();

        postEntity.setContent( request.getContent() );
        postEntity.setUserId( request.getUserId() );

        return postEntity;
    }

    @Override
    public PostResponse toResponse(PostEntity postentity) {
        if ( postentity == null ) {
            return null;
        }

        PostResponse postResponse = new PostResponse();

        postResponse.setId( postentity.getId() );
        postResponse.setContent( postentity.getContent() );

        return postResponse;
    }
}
