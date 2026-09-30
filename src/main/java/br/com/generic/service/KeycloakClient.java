package br.com.generic.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UsersResource;

@ApplicationScoped
public class KeycloakClient {

    private final Keycloak keycloak;

    @ConfigProperty(name = "keycloak.realm")
    String realmName;

    @ConfigProperty(name = "quarkus.keycloak.admin-client.client-id")
    String clientId;

    @ConfigProperty(name = "quarkus.keycloak.admin-client.client-secret")
    String clientSecret;

    public KeycloakClient(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public RealmResource realm() {
        return keycloak.realm(realmName);
    }

    public UsersResource users() {
        return realm().users();
    }

    public RolesResource roles() {
        return realm().roles();
    }
}