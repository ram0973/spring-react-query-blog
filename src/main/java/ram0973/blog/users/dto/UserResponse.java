package ram0973.blog.users.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ram0973.blog.roles.RolesArraySerializer;
import ram0973.blog.roles.UserRole;

import java.util.List;

public record UserResponse(
    Integer id,
    String avatar,
    String email,
    boolean enabled,
    String firstName,
    String lastName,
    @JsonSerialize(using = RolesArraySerializer.class)
    List<UserRole> roles) {
}
