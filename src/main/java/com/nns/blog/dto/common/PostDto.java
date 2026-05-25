package com.nns.blog.dto.common;

import com.nns.blog.entities.Comment;
import com.nns.blog.entities.Post;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public record PostDto(
    Long postId,
    String title,
    String content,
    String imageName,
    Date addedDate,
    CategoryDto category,
    UserDto user,
    Set<CommentDto> comments
) {
    public static PostDto from(Post post) {

        Set<CommentDto> commentDtos = post.getComments().stream().map(CommentDto::from).collect(Collectors.toSet());

        return new PostDto(post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getImageName(),
                post.getAddedDate(),
                CategoryDto.from(post.getCategory()),
                UserDto.from(post.getUser()),
                commentDtos
                );
    }

    public PostDto updateImageName(String imgName) {
        return new PostDto(postId,
                title,
                content,
                imgName,
                addedDate,
                category,
                user,
                comments
        );
    }
}
