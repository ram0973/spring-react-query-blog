package ram0973.blog.posts.dto;

import java.util.List;

public record PostsResponse(
    List<PostResponse> posts,
    int currentPage,
    long totalItems,
    int totalPages) {
}
