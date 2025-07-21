package ram0973.blog.posts;

import org.mapstruct.*;
import ram0973.blog.common.mappers.JsonNullableMapper;
import ram0973.blog.common.mappers.ReferenceMapper;
import ram0973.blog.posts.dto.PostCreateRequest;
import ram0973.blog.posts.dto.PostResponse;
import ram0973.blog.posts.dto.PostUpdateRequest;

@Mapper(
    uses = {JsonNullableMapper.class, ReferenceMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
//@RequiredArgsConstructor
//@NoArgsConstructor(force = true)
public abstract class PostMapper {

    public abstract Post map(PostCreateRequest dto);

    public abstract PostResponse map(Post post);

    public abstract void update(@MappingTarget Post post, PostUpdateRequest dto);
}
