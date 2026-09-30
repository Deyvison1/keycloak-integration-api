package br.com.generic.dto.request;

import java.util.List;

public record UserCreateRequest(
        String username,
        String email,
        String firstName,
        String lastName,
        Boolean enabled,
        Boolean emailVerified,
        String password,
        List<String> roleIds
) {}