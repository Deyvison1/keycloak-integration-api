package br.com.generic.mapper;

import br.com.generic.dto.request.UserCreateRequest;
import br.com.generic.dto.response.UserResponse;
import br.com.generic.dto.request.UserUpdateRequest;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    @Mapping(
            target = "credentials",
            expression = "java(toCredentials(request.password()))"
    )
    UserRepresentation toKeycloak(UserCreateRequest request);

    UserRepresentation toKeycloak(UserUpdateRequest request);

    UserResponse toResponse(UserRepresentation user);

    default List<CredentialRepresentation> toCredentials(String password) {
        if (password == null || password.isBlank()) {
            return List.of();
        }

        var credential = new CredentialRepresentation();

        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);

        return List.of(credential);
    }
}