package ram0973.blog.posts.dto;

public record PostResponse(
    String id,
    String slug,
    String title,
    String excerpt,
    String content,
    String contentJson,
    String image,
    boolean enabled,
    String createdDate) {
}
