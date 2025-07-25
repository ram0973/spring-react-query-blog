package ram0973.blog.users;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ram0973.blog.common.exceptions.EntityAlreadyExistsException;
import ram0973.blog.common.exceptions.ForbiddenOperationException;
import ram0973.blog.common.exceptions.NoSuchEntityException;
import ram0973.blog.common.utils.PagedEntityUtils;
import ram0973.blog.users.dto.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("UnnecessaryLocalVariable")
@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${app.mailing.admin-email}")
    private String adminEmail;

    public AllUsersResponse findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = users.stream().map(userMapper::map).toList();
        var dto = new AllUsersResponse(userResponses);
        return dto;
    }

    public UsersResponse findAllPaged(int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(PagedEntityUtils.getSortOrders(sort)));
        Page<User> pagedUsers = userRepository.findAll(pageable);
        List<User> users = pagedUsers.getContent();
        List<UserResponse> userResponses = users.stream().map(userMapper::map).toList();
        UsersResponse dto = new UsersResponse(userResponses, pagedUsers.getNumber(),
            pagedUsers.getTotalElements(), pagedUsers.getTotalPages());
        return dto;
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Transactional
    public User createUser(@NotNull UserCreateRequest dto) {
        Optional<User> optionalUser = findUserByEmailIgnoreCase(dto.email().strip());
        if (optionalUser.isPresent()) {
            throw new EntityAlreadyExistsException("Email already in use");
        }
        User user = userMapper.map(dto);
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(int id, @NotNull UserUpdateRequest dto) throws IOException {
        User user = userRepository.findById(id).orElseThrow(
            () -> new NoSuchEntityException("No such User with id: " + id));
        userMapper.update(user, dto);
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        User user = findById(id).orElseThrow(
            () -> new NoSuchEntityException("No such User with id: " + id));
        if (user.getEmail().equals(adminEmail)) {
            throw new ForbiddenOperationException("You cannot delete admin account");
        }
        userRepository.deleteById(id);
    }
}
