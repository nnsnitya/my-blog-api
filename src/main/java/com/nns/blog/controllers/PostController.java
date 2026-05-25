package com.nns.blog.controllers;

import com.nns.blog.constants.ApiConstants;
import com.nns.blog.constants.AppConstants;
import com.nns.blog.dto.common.PostDto;
import com.nns.blog.dto.responses.Code;
import com.nns.blog.dto.responses.PostResponse;
import com.nns.blog.dto.responses.ResponseHandler;
import com.nns.blog.services.FileService;
import com.nns.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(ApiConstants.VERSION1 +"/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

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
    public ResponseEntity<?> getPostsByUser(
            @PathVariable Long userId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PostResponse postsResp = postService.getPostByUser(userId, pageNumber, pageSize, sortBy, sortDir);
        return ResponseHandler.generateResp("post by user", HttpStatus.OK, postsResp, Code.SUCCESS.getCode());
    }

    //get by category
    @GetMapping("/category/{catId}")
    public ResponseEntity<?> getPostsByCategory(
            @PathVariable Long catId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy
    ) {
        PostResponse postResp = postService.getPostByCategory(catId, pageNumber, pageSize, sortBy);
        return ResponseHandler.generateResp("post by category", HttpStatus.OK, postResp, Code.SUCCESS.getCode());
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
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
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
        return ResponseHandler.generateResp("Post updated !!", HttpStatus.OK, post1, Code.SUCCESS.getCode());
    }

    //search
    @GetMapping("/search/{key}")
    public ResponseEntity<?> searchPosts(@PathVariable String key) {
        List<PostDto> postDtos = postService.searchPosts(key);
        return ResponseHandler.generateResp("Search Result..", HttpStatus.OK, postDtos, Code.SUCCESS.getCode());
    }

    //post image upload
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<Object> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                  @PathVariable Long postId) throws IOException {
        PostDto postDto = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        PostDto postDto1 = postDto.updateImageName(fileName);
        PostDto updatePost = postService.updatePost(postDto1, postId);

        return ResponseHandler.generateResp("Image uploaded", HttpStatus.OK, updatePost, Code.SUCCESS.getCode());
    }

    //method to serve files
    @GetMapping(value = "image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName,
                              HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
