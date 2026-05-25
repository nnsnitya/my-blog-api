package com.nns.blog.controllers;

import com.nns.blog.constants.ApiConstants;
import com.nns.blog.dto.common.CommentDto;
import com.nns.blog.dto.responses.Code;
import com.nns.blog.dto.responses.ResponseHandler;
import com.nns.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.VERSION1+"/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<Object> createComment(@RequestBody CommentDto commentDto,
                                                @PathVariable Long postId,
                                                @PathVariable Long userId
    ) {
        CommentDto createdComment = commentService.createComment(commentDto, postId, userId);
        return ResponseHandler.generateResp("Comment Created", HttpStatus.CREATED, createdComment, Code.SUCCESS.getCode());
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseHandler.generateResp("Comment Deleted", HttpStatus.OK, null, Code.SUCCESS.getCode());
    }

}
