package com.nns.blog.services;

import com.nns.blog.dto.common.PostDto;
import com.nns.blog.dto.responses.PostResponse;
import java.util.List;

public interface PostService {
    //create
    PostDto createPost(PostDto postDto, Long userId, Long catId);

    //update
    PostDto updatePost(PostDto postDto, Long postId);

    //delete
    void deletePost(Long postId);

    //get All posts
    PostResponse getAllPost(Integer pageNumber, Integer pageSize);

    //get Single post
    PostDto getPostById(Long postId);

    //get All posts by category
    PostResponse getPostByCategory(Long catId, Integer pageNumber, Integer pageSize, String sortBy);

    //get All posts by user
    PostResponse getPostByUser(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //search posts
    List<PostDto> searchPosts(String keyword);
}
