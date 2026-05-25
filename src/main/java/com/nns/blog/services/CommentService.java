package com.nns.blog.services;

import com.nns.blog.dto.common.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Long postId, Long userId);

    void deleteComment(Long commentId);
}
