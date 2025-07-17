package ram0973.blog.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ram0973.blog.users.User;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    Optional<UserRole> findByRole(@Param(value = "role") User.Role role);

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE user_role RESTART IDENTITY CASCADE", nativeQuery = true)
    void truncateTable();
}
