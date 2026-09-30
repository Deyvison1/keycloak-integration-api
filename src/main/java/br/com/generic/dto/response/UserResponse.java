package br.com.generic.dto.response;

import java.util.List;
import java.util.Set;

public record UserResponse(
        String id,
        String username,
        String email,
        String firstName,
        String lastName,
        Boolean enabled,
        Boolean emailVerified,
        List<RoleResponse> roles
) {}