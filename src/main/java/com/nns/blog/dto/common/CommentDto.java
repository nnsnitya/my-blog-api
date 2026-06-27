package com.nns.blog.dto.common;

public record CommentDto(
        Long id,
        String content,
        Long userId,
        String userName
) {
}
