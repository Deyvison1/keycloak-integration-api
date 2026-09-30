package br.com.generic.service;

import br.com.generic.dto.response.RoleResponse;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class RoleServiceImpl {

    private final KeycloakClient keycloakClient;

    public RoleServiceImpl(KeycloakClient keycloakClient) {
        this.keycloakClient = keycloakClient;
    }

    public List<RoleResponse> findAll() {
        return keycloakClient
                .roles()
                .list()
                .stream()
                .map(role -> new RoleResponse(
                        role.getId(),
                        role.getName()
                ))
                .toList();
    }
}
