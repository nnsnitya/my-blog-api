package com.nns.blog.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record JwtAuthRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
