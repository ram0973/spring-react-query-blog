package ram0973.blog.users;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ram0973.blog.common.exceptions.NoSuchEntityException;
import ram0973.blog.common.mappers.JsonNullableMapper;
import ram0973.blog.common.mappers.ReferenceMapper;
import ram0973.blog.roles.UserRole;
import ram0973.blog.roles.UserRoleRepository;
import ram0973.blog.users.dto.UserCreateRequest;
import ram0973.blog.users.dto.UserResponse;
import ram0973.blog.users.dto.UserUpdateRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Mapper(
    uses = {JsonNullableMapper.class, ReferenceMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public abstract class UserMapper {

    @Autowired
    private final UserRoleRepository userRoleRepository;
    @Autowired
    private final PasswordEncoder encoder;

    @Mapping(target = "password", qualifiedByName = "mapEncodePassword")
    @Mapping(target = "roles", qualifiedByName = "mapRolesStringsToEntities")
    public abstract User map(UserCreateRequest dto);

    public abstract UserResponse map(User user);

    @Mapping(target = "roles", qualifiedByName = "mapRolesStringsToEntities")
    public abstract void update(@MappingTarget User user, UserUpdateRequest dto);

    @Named("mapEncodePassword")
    public String map(String password) {
        return encoder.encode(password);
    }

    @Named("mapRolesStringsToEntities")
    public Set<UserRole> map(List<User.Role> roles) {
        Set<UserRole> result = new HashSet<>();
        if (roles == null || roles.isEmpty()) {
            return result;
        }
        for (User.Role role : roles) {
            UserRole userRole =
                userRoleRepository.findByRole(role).orElseThrow(() -> new NoSuchEntityException(role.name()));
            result.add(userRole);
        }
        return result;
    }
}
