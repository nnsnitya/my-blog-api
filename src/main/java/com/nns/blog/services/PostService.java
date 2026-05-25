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
    List<PostDto> getPostByCategory(Long catId);

    //get All posts by user
    List<PostDto> getPostByUser(Long userId);

}
