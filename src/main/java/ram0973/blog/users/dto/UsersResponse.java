package ram0973.blog.users.dto;

import java.util.List;

public record UsersResponse(
    List<UserResponse> users,
    int currentPage,
    long totalItems,
    int totalPages) {
}
