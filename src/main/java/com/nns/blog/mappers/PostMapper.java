package com.nns.blog.mappers;

import com.nns.blog.dto.common.PostDto;
import com.nns.blog.entities.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto toDto(Post post);

    Post toEntity(PostDto postDto);

    List<PostDto> toDtoList(List<Post> posts);
}
