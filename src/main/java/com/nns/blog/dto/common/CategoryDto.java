package com.nns.blog.dto.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDto(
        Long categoryId,
        @NotBlank
        @Size(min = 4, message = "Min size of category title is 4")
        String categoryTitle,
        @NotBlank
        @Size(min = 10, message = "Min size of category description is 10")
        String categoryDescription
) {
}
