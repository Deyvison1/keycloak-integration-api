package br.com.generic.dto.request;

public record UserPasswordRequest(
        String password,
        Boolean temporary
) {}
