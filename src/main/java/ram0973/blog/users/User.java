package ram0973.blog.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ram0973.blog.common.entity.BaseEntity;
import ram0973.blog.roles.RolesArraySerializer;
import ram0973.blog.roles.UserRole;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "user_")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {
    @Column(nullable = false, unique = true)
    @NotBlank
    @Email
    private String email;

    private String firstName;

    private String lastName;

    private String avatar;

    @Column(nullable = false)
    @NotBlank
    @JsonIgnore
    private String password;

    //@Builder.Default
    private boolean enabled;

    @JsonSerialize(using = RolesArraySerializer.class)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> roles;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            return new HashSet<>();
        }
        return this
            .getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().toString()))
            .collect(Collectors.toSet());
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    public void addRole(UserRole role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }

    @Getter
    public enum Role {
        ADMIN("Admin"),
        MODERATOR("Moderator"),
        USER("User");

        private final String label;

        Role(String label) {
            this.label = label;
        }
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
