package br.com.generic.dto.response;

import java.util.List;

public record UserPageResponse(
        List<UserResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}