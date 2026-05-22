package com.nns.blog.dto.common;

public record CategoryDto(
        Long categoryId,
        String categoryTitle,
        String categoryDescription
) {
}
