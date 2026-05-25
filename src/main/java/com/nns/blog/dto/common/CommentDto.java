package com.nns.blog.dto.common;

import com.nns.blog.entities.Comment;

public record CommentDto(
        Long id,
        String content,
        Long userId,
        String userName
) {

    public static CommentDto from(Comment comment) {

        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getUser().getName()
        );
    }
}
