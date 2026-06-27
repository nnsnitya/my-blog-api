package com.nns.blog.services.impl;

import com.nns.blog.dto.common.CategoryDto;
import com.nns.blog.dto.common.PostDto;
import com.nns.blog.dto.responses.PostResponse;
import com.nns.blog.entities.Category;
import com.nns.blog.entities.Post;
import com.nns.blog.entities.User;
import com.nns.blog.exceptions.ResourceNotFoundException;
import com.nns.blog.mappers.CategoryMapper;
import com.nns.blog.repositories.CategoryRepository;
import com.nns.blog.repositories.PostRepository;
import com.nns.blog.repositories.UserRepository;
import com.nns.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long catId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Category cat = categoryRepo.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", catId));

        Post post = Post.builder()
                .title(postDto.title())
                .content(postDto.content())
                .build();
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(cat);

        Post newPost = postRepo.save(post);
        return PostDto.from(newPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        post.setTitle(postDto.title());
        post.setContent(postDto.content());
        post.setImageName(postDto.imageName());

        CategoryDto catDto = postDto.category();
        Category cat = categoryMapper.toEntity(catDto);

        post.setCategory(cat);
        Post updatedPost = postRepo.save(post);
        return PostDto.from(updatedPost);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Post> pagePosts = postRepo.findAll(page);
        //List<Post> allPosts = pagePosts.getContent();
        //List<PostDto> posts = allPosts.stream().map(p -> Mapper.mapToPostDto(p)).toList();
        return PostResponse.from(pagePosts);
    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        return PostDto.from(post);
    }

    @Override
    public PostResponse getPostByCategory(Long catId, Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

        Category cat = categoryRepo.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Id",catId));
//        List<Post> postsByCat = postRepo.findByCategory(cat);

        Page<Post> posts = postRepo.findByCategory(cat, pageable);
        return PostResponse.from(posts);
    }

    @Override
    public PostResponse getPostByUser(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Page<Post> postsByUser = postRepo.findByUser(user, pageable);
        return PostResponse.from(postsByUser);
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepo.findByTitleContaining(keyword);
        List<PostDto> list = posts.stream().map(p -> PostDto.from(p)).toList();
        return list;
    }

}
