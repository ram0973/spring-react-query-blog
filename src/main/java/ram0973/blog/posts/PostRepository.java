package ram0973.blog.posts;

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
public interface PostRepository extends JpaRepository<Post, Integer> {

    //@Query("SELECT p FROM Post p WHERE p.slug = :slug")
    Optional<Post> findBySlug(@Param(value = "slug") String slug);

    @NonNull
    Page<Post> findAll(@NonNull Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE post_ RESTART IDENTITY CASCADE", nativeQuery = true)
    void truncateTable();
}
