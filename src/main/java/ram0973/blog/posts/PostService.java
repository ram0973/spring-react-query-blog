package ram0973.blog.posts;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ram0973.blog.common.exceptions.EntityAlreadyExistsException;
import ram0973.blog.common.exceptions.NoSuchEntityException;
import ram0973.blog.common.utils.PagedEntityUtils;
import ram0973.blog.posts.dto.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("UnnecessaryLocalVariable")
@Service
@RequiredArgsConstructor
@Log4j2
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public AllPostsResponse findAll() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = posts.stream().map(postMapper::map).toList();
        AllPostsResponse dto = new AllPostsResponse(postResponses);
        return dto;
    }

    public PostsResponse findAllPaged(int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(PagedEntityUtils.getSortOrders(sort)));
        Page<Post> pagedPosts = postRepository.findAll(pageable);
        List<Post> posts = pagedPosts.getContent();
        List<PostResponse> postResponses = posts.stream().map(postMapper::map).toList();
        PostsResponse dto = new PostsResponse(postResponses, pagedPosts.getNumber(),
            pagedPosts.getTotalElements(), pagedPosts.getTotalPages());
        return dto;
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    public Optional<Post> findPostBySlug(String slug) {
        return postRepository.findBySlug(slug);
    }

    @Transactional
    public Post createPost(@NotNull PostCreateRequest dto) {
        Optional<Post> optionalPost = findPostBySlug(dto.slug().strip());
        if (optionalPost.isPresent()) {
            throw new EntityAlreadyExistsException("Slug already in use");
        }
        Post post = postMapper.map(dto);
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(int id, @NotNull PostUpdateRequest dto) throws IOException {
        Post post = postRepository.findById(id).orElseThrow(
            () -> new NoSuchEntityException("No such Post with id: " + id));
        if (!Objects.equals(dto.slug().strip(), post.getSlug())) {
            Optional<Post> optionalPost = findPostBySlug(dto.slug().strip());
            if (optionalPost.isPresent()) {
                throw new EntityAlreadyExistsException("Slug already in use");
            }
        }
        postMapper.update(post, dto);
        return postRepository.save(post);
    }

    public void deletePost(int id) {
        Post post = postRepository.findById(id).orElseThrow(
            () -> new NoSuchEntityException("No such Post with id: " + id));
        postRepository.deleteById(id);
    }
}
