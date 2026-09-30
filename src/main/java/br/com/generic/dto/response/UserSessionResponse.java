package br.com.generic.dto.response;

public record UserSessionResponse(
        String id,
        String username,
        String ipAddress,
        String start,
        String lastAccess,
        String clients
) {}
