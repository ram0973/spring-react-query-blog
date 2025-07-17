package ram0973.blog.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import ram0973.blog.users.User;

import java.util.List;

@Builder
public record UserCreateRequest(
    String avatar,
    @NotBlank @Email String email,
    Boolean enabled,
    String firstName,
    String lastName,
    @NotBlank
    @Size(min = 6, message = "Password must have 6 symbols or more")
    @Size(max = 64, message = "Password length have more than 64 symbols")
    String password,
    //@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    List<User.Role> roles
) {
}
