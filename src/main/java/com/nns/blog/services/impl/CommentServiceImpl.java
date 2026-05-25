package com.nns.blog.services.impl;

import com.nns.blog.dto.common.CommentDto;
import com.nns.blog.entities.Comment;
import com.nns.blog.entities.Post;
import com.nns.blog.exceptions.ResourceNotFoundException;
import com.nns.blog.repositories.CommentRepository;
import com.nns.blog.repositories.PostRepository;
import com.nns.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepo;
    @Autowired
    private CommentRepository commentRepo;


    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post", "Post Id", postId));
        Comment comment = Comment.builder()
                .content(commentDto.content())
                .build();
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);
        return CommentDto.from(savedComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment com = commentRepo.findById(commentId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Comment", "Comment Id", commentId));
        commentRepo.delete(com);
    }
}
