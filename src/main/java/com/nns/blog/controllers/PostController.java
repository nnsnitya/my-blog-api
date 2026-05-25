package com.nns.blog.controllers;

import com.nns.blog.constants.ApiConstants;
import com.nns.blog.dto.common.PostDto;
import com.nns.blog.dto.responses.Code;
import com.nns.blog.dto.responses.PostResponse;
import com.nns.blog.dto.responses.ResponseHandler;
import com.nns.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.VERSION1 +"/posts")
public class PostController {

    @Autowired
    private PostService postService;

    //create
    @PostMapping("/user/{userId}/category/{catId}")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto,
                                        @PathVariable Long userId,
                                        @PathVariable Long catId) {
        PostDto createPostDto = postService.createPost(postDto, userId, catId);
        return ResponseHandler.generateResp("Post created", HttpStatus.CREATED, createPostDto, Code.SUCCESS.getCode());
    }

    //get by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getPostsByUser(@PathVariable Long userId) {
        List<PostDto> postsByUser = postService.getPostByUser(userId);
        return ResponseHandler.generateResp("post by user", HttpStatus.OK, postsByUser, Code.SUCCESS.getCode());
    }

    //get by category
    @GetMapping("/category/{catId}")
    public ResponseEntity<?> getPostsByCategory(@PathVariable Long catId) {
        List<PostDto> postsByCategory = postService.getPostByCategory(catId);
        return ResponseHandler.generateResp("post by category", HttpStatus.OK, postsByCategory, Code.SUCCESS.getCode());
    }

    //get post by id
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        PostDto postById = postService.getPostById(postId);
        return ResponseHandler.generateResp("Post By Id", HttpStatus.OK, postById, Code.SUCCESS.getCode());
    }

    //get all posts
    @GetMapping("/")
    public ResponseEntity<?> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
    ) {
        PostResponse postResp = postService.getAllPost(pageNumber, pageSize);
        return ResponseHandler.generateResp("All Posts", HttpStatus.OK, postResp, Code.SUCCESS.getCode());
    }

    //delete post
    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseHandler.generateResp("Post deleted!!", HttpStatus.OK, null, Code.SUCCESS.getCode());
    }

    //update post
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId) {
        PostDto post1 = postService.updatePost(postDto, postId);
        return ResponseHandler.generateResp("Post updated !!", HttpStatus.OK, null, Code.SUCCESS.getCode());
    }
}
