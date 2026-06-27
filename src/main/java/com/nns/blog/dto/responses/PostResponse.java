package com.nns.blog.dto.responses;

import com.nns.blog.dto.common.PostDto;
import com.nns.blog.entities.Post;
import com.nns.blog.mappers.PostMapper;
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
    public static PostResponse from(Page<Post> postPage, PostMapper mapper) {
        List<Post> posts = postPage.getContent();
        return new PostResponse(mapper.toDtoList(posts),
                postPage.getNumber(),
                postPage.getSize(),
                postPage.getTotalElements(),
                postPage.getTotalPages(),
                postPage.isLast()
        );
    }
}
