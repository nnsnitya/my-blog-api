package com.nns.blog.mappers;

import com.nns.blog.dto.common.CommentDto;
import com.nns.blog.entities.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toDto(Comment comment);

    Comment toEntity(CommentDto commentDto);
}
