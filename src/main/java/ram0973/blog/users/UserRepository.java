package ram0973.blog.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //@Query("SELECT p FROM User p WHERE p.email = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(@Param(value = "email") String email);

    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE user_ RESTART IDENTITY CASCADE", nativeQuery = true)
    void truncateTable();
}
