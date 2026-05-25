package com.nns.blog.dto.common;

import com.nns.blog.entities.Post;

import java.util.Date;

public record PostDto(
    Long postId,
    String title,
    String content,
    String imageName,
    Date addedDate,
    CategoryDto category,
    UserDto user
) {
    public static PostDto from(Post post) {
        return new PostDto(post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getImageName(),
                post.getAddedDate(),
                CategoryDto.from(post.getCategory()),
                UserDto.from(post.getUser())
                );
    }

    public PostDto updateImageName(String imgName) {
        return new PostDto(postId,
                title,
                content,
                imgName,
                addedDate,
                category,
                user
        );
    }
}
