package com.nns.blog.mappers;

import com.nns.blog.dto.common.CategoryDto;
import com.nns.blog.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);
}
