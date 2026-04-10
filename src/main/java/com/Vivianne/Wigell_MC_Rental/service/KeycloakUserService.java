package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.UpdateUserDto;
import com.groupc.shared.exception.ResourceNotFoundException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class KeycloakUserService {

    private final Keycloak keycloak;
    private final String realm;
    private final Logger logger = LoggerFactory.getLogger(KeycloakUserService.class);

    public KeycloakUserService(Keycloak keycloak, @Value("${keycloak.admin.realm}") String realm) {
        this.keycloak = keycloak;
        this.realm = realm;
    }

    public String createUserKeycloak(String firstName, String lastName,
                                     String username, String password, String role) {
        var userResource = realm().users();

        List<UserRepresentation> existingUsers = userResource.searchByUsername(username, true);
        if (!existingUsers.isEmpty()) {
            logger.info("Användaren '{}' finns redan i Keycloak", username);
            return existingUsers.get(0).getId();
        }
        //Skapa användare
        var userRep = new UserRepresentation();
        userRep.setUsername(username);
        userRep.setFirstName(firstName);
        userRep.setLastName(lastName);
        userRep.setEnabled(true);

        try (var response = userResource.create(userRep)) {
            if (response.getStatus() == 409) {
                return userResource.searchByUsername(username, true).get(0).getUsername();
            }
            if (response.getStatus() != 201) {
                throw new ResourceNotFoundException("Kunde inte skapa användare, Status: " + response.getStatus());
            }
            String userId = org.keycloak.admin.client.CreatedResponseUtil.getCreatedId(response);

            //Lösen
            var cred = new CredentialRepresentation();
            cred.setType(CredentialRepresentation.PASSWORD);
            cred.setValue(password);
            cred.setTemporary(false);
            userResource.get(userId).resetPassword(cred);

            //tilldela rollen
            assignRole(userId, role);

            logger.info("Ny keycloak användare skapad");

            return userId;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } return existingUsers.getFirst().getUsername();
    }

    public void assignRole(String userId, String role) {
        //tilldela rollen
        RoleRepresentation userRole = realm().roles().get(role).toRepresentation();
        realm().users().get(userId).roles().realmLevel().add(List.of(userRole));
        logger.info("Lagt till rollen '{}'", role);
    }

    public void updateUserProfile(String userId, UpdateUserDto profile) {
    Objects.requireNonNull(userId, "userId får inte vara null");
    Objects.requireNonNull(profile, "profilen får inte vara null");
    UserResource u = user(userId);
    UserRepresentation current = u.toRepresentation();

    if (profile.firstName() != null) {
        current.setFirstName(profile.firstName());
    }
    if (profile.lastName() != null) {
        current.setLastName(profile.lastName());
    }
    logger.info("Uppdaterar användaren '{}'", current.getUsername());
    u.update(current);
    }

    public void delete (String userId) {
        logger.info("Tar bort keycloak användaren med id '{}'", userId);
        user(userId).remove();
    }

    private RealmResource realm() { return keycloak.realm(realm); }
    private UserResource user(String userId) { return realm().users().get(userId); }
}
