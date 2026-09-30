package br.com.generic.dto.response;

public record UserRoleResponse(
        String id,
        String name,
        String description,
        Boolean composite,
        Boolean clientRole
) {}
