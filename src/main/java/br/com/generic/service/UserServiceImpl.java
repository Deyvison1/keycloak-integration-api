package br.com.generic.service;

import br.com.generic.dto.request.UserCreateRequest;
import br.com.generic.dto.request.UserUpdateRequest;
import br.com.generic.dto.response.RoleResponse;
import br.com.generic.dto.response.UserResponse;
import br.com.generic.mapper.UserMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@ApplicationScoped
public class UserServiceImpl {

    private final KeycloakClient keycloakClient;
    private final UserMapper userMapper;

    public UserServiceImpl(
            KeycloakClient keycloakClient,
            UserMapper userMapper
    ) {
        this.keycloakClient = keycloakClient;
        this.userMapper = userMapper;
    }

    public List<UserResponse> findAll() {
        return keycloakClient
                .users()
                .list()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UserResponse findById(String id) {
        var user = keycloakClient
                .users()
                .get(id)
                .toRepresentation();

        return toResponse(user);
    }

    public UserResponse create(UserCreateRequest request) {
        var user = userMapper.toKeycloak(request);

        try (var response = keycloakClient.users().create(user)) {

            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                throw new IllegalStateException(
                        "Não foi possível criar o usuário"
                );
            }

            var location = response.getLocation();

            var id = location.getPath()
                    .substring(location.getPath().lastIndexOf('/') + 1);

            var userResource = keycloakClient
                    .users()
                    .get(id);

            updateRoles(userResource, request.roleIds());

            return findById(id);
        }
    }

    public UserResponse update(
            String id,
            UserUpdateRequest request
    ) {
        var userResource = keycloakClient
                .users()
                .get(id);

        var user = userMapper.toKeycloak(request);

        userResource.update(user);

        updateRoles(userResource, request.roleIds());
        updatePasswordIfProvided(userResource, request.password());

        return findById(id);
    }

    private void updatePasswordIfProvided(
            UserResource userResource,
            String password
    ) {
        if (password == null || password.isBlank()) {
            return;
        }

        var credential = new CredentialRepresentation();

        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);

        userResource.resetPassword(credential);
    }

    private void updateRoles(
            UserResource userResource,
            List<String> roleIds
    ) {
        var currentRoles = userResource
                .roles()
                .realmLevel()
                .listAll();

        var roles = keycloakClient
                .roles()
                .list()
                .stream()
                .filter(role -> roleIds.contains(role.getId()))
                .toList();

        if (roles.size() != roleIds.size()) {
            throw new IllegalArgumentException(
                    "Uma ou mais roles não foram encontradas"
            );
        }

        if (!currentRoles.isEmpty()) {
            userResource
                    .roles()
                    .realmLevel()
                    .remove(currentRoles);
        }

        if (!roles.isEmpty()) {
            userResource
                    .roles()
                    .realmLevel()
                    .add(roles);
        }
    }

    public void delete(String id) {
        keycloakClient
                .users()
                .get(id)
                .remove();
    }

    private UserResponse toResponse(UserRepresentation user) {
        var response = userMapper.toResponse(user);

        var roles = keycloakClient
                .users()
                .get(user.getId())
                .roles()
                .realmLevel()
                .listAll()
                .stream()
                .map(role -> new RoleResponse(
                        role.getId(),
                        role.getName()
                ))
                .toList();

        return new UserResponse(
                response.id(),
                response.username(),
                response.email(),
                response.firstName(),
                response.lastName(),
                response.enabled(),
                response.emailVerified(),
                roles
        );
    }
}