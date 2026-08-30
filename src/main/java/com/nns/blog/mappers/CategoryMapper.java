package com.nns.blog.mappers;

import com.nns.blog.dto.common.CategoryDto;
import com.nns.blog.entities.Category;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "categoryDesc", target = "categoryDescription")
    CategoryDto toDto(Category category);

    @InheritInverseConfiguration
    Category toEntity(CategoryDto categoryDto);
}
