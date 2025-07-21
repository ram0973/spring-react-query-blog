package ram0973.blog.posts;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ram0973.blog.common.exceptions.NoSuchEntityException;
import ram0973.blog.posts.dto.AllPostsResponse;
import ram0973.blog.posts.dto.PostCreateRequest;
import ram0973.blog.posts.dto.PostUpdateRequest;
import ram0973.blog.posts.dto.PostsResponse;

import java.io.IOException;

@SuppressWarnings("UnnecessaryLocalVariable")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Log4j2
public class PostController {

    private final PostService postService;

    @GetMapping("/posts-all")
    //@PreAuthorize("hasRole('ADMIN')")
    public AllPostsResponse getPosts(
        HttpServletResponse response
    ) {
        AllPostsResponse responseBody = postService.findAll();
        response.addHeader("X-Total-Count", String.valueOf(responseBody.posts().size()));
        return responseBody;
    }

    @GetMapping("/posts")
    //@PreAuthorize("hasRole('ADMIN')") // TODO: check everywhere for opportunity to use Post.Role.ADMIN
    public PostsResponse getPosts(
        @RequestParam(required = false) String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,desc") String[] sort,
        HttpServletResponse response
    ) {
        PostsResponse responseBody = postService.findAllPaged(page, size, sort);
        response.addHeader("X-Total-Count", String.valueOf(responseBody.posts().size()));
        return responseBody;
    }

    @GetMapping("/posts/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Post getPostById(@PathVariable("id") int id) {
        Post post = postService.findById(id).orElseThrow(
            () -> new NoSuchEntityException("No such post with id: " + id));
        return post;
    }

    @PostMapping("/posts")
    //@PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@Valid @RequestBody PostCreateRequest dto) throws IOException {
        Post post = postService.createPost(dto);
        return post;
    }

    @PatchMapping(path = "/posts/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Post updatePost(
        @PathVariable("id") int id, @Valid @RequestBody PostUpdateRequest dto) throws IOException {
        Post post = postService.updatePost(id, dto);
        return post;
    }

    @DeleteMapping("/posts/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") int id) {
        postService.deletePost(id);
    }
}
