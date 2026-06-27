package com.nns.blog.dto.common;

import java.util.Date;
import java.util.Set;

public record PostDto(
    Long postId,
    String title,
    String content,
    String imageName,
    Date addedDate,
    CategoryDto category,
    Long userId,
    String userName,
    Set<CommentDto> comments
) {

    public PostDto updateImageName(String imgName) {
        return new PostDto(postId,
                title,
                content,
                imgName,
                addedDate,
                category,
                userId,
                userName,
                comments
        );
    }
}
