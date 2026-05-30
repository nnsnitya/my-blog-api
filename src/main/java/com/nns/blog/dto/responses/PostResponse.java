package com.nns.blog.dto.responses;

import com.nns.blog.dto.common.PostDto;
import com.nns.blog.entities.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public record PostResponse(
        List<PostDto> posts,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean lastPage
) {
    public static PostResponse from(Page<Post> postPage) {
        List<Post> posts = postPage.getContent();
        List<PostDto> content = posts.stream().map(p -> PostDto.from(p)).toList();
        return new PostResponse(content,
                postPage.getNumber(),
                postPage.getSize(),
                postPage.getTotalElements(),
                postPage.getTotalPages(),
                postPage.isLast()
        );
    }
}
