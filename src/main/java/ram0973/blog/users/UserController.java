package ram0973.blog.users;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ram0973.blog.common.exceptions.NoSuchEntityException;
import ram0973.blog.users.dto.UserCreateRequest;
import ram0973.blog.users.dto.UserUpdateRequest;
import ram0973.blog.users.dto.UsersResponse;

import java.io.IOException;

@SuppressWarnings("UnnecessaryLocalVariable")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')") // TODO: check everywhere for opportunity to use User.Role.ADMIN
    public UsersResponse getUsers(
        @RequestParam(required = false) String email,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,desc") String[] sort,
        HttpServletResponse response
    ) {
        UsersResponse responseBody = userService.findAllPaged(page, size, sort);
        response.addHeader("X-Total-Count", String.valueOf(responseBody.users().size()));
        return responseBody;
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public User getUserById(@PathVariable("id") int id) {
        User user = userService.findById(id).orElseThrow(
            () -> new NoSuchEntityException("No such user with id: " + id));
        return user;
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody UserCreateRequest dto) throws IOException {
        User user = userService.createUser(dto);
        return user;
    }

    @PatchMapping(path = "{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public User updateUser(
        @PathVariable("id") int id, @Valid @RequestBody UserUpdateRequest dto) throws IOException {
        User user = userService.updateUser(id, dto);
        return user;
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
    }
}
