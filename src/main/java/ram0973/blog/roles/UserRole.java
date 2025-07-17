package ram0973.blog.roles;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ram0973.blog.common.entity.BaseEntity;
import ram0973.blog.users.User;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_role")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserRole extends BaseEntity {

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private User.Role role;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    @Builder.Default
    private List<User> users = new ArrayList<>();
}

