package ram0973.blog.posts;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ram0973.blog.common.entity.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "post_")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity {
    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(max = 255)
    private String slug;

    @NotBlank
    @Size(max = 255)
    private String title;

    @Column(name = "excerpt", columnDefinition = "TEXT")
    private String excerpt;


    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "contentJson", columnDefinition = "TEXT")
    private String contentJson;

    private String image;

    //@Builder.Default
    private boolean enabled;
}
