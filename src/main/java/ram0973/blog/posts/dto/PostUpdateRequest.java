package ram0973.blog.posts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PostUpdateRequest(
        @NotBlank @Size(max = 255) String slug,
        @NotBlank @Size(max = 255) String title,
        String excerpt,
        String content,
        String contentJson,
        String image,
        boolean enabled
) {
}
