package com.nns.blog.mappers;

import com.nns.blog.dto.responses.PostImageDto;
import com.nns.blog.entities.PostImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostImageMapper {
    PostImageDto toDto(PostImage postImage);

    PostImage toEntity(PostImageDto postImageDto);

//    List<PostDto> toDtoList(List<Post> posts);
}
