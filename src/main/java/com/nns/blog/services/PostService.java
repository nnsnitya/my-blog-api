package com.nns.blog.services;

import com.nns.blog.dto.common.PostDto;
import com.nns.blog.dto.responses.PostImageDto;
import com.nns.blog.dto.responses.PostResponse;
import com.nns.blog.entities.PostImage;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

    //upload post image to minio
    PostImageDto uploadPostImage(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    PostDto getPostDtoBlankObj();
}
