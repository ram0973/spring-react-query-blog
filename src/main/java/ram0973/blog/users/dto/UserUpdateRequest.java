package ram0973.blog.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import ram0973.blog.users.User;

import java.util.List;

@Builder
public record UserUpdateRequest(
    String avatar,
    @NotBlank @Email String email,
    boolean enabled,
    String firstName,
    String lastName,
    String password,
    //@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    List<User.Role> roles
) {
}
