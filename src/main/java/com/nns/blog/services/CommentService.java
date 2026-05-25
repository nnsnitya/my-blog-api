package com.nns.blog.services;

import com.nns.blog.dto.common.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Long postId);

    void deleteComment(Long commentId);
}
