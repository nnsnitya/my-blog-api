package com.nns.blog.dto.common;

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
}
