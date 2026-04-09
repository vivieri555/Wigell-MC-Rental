package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.UpdateUserDto;
import com.Vivianne.Wigell_MC_Rental.entity.Address;
import com.groupc.shared.exception.ResourceNotFoundException;
import org.keycloak.admin.client.CreatedResponseUtil;
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

    public KeycloakUserService(Keycloak keycloak, @Value("${keycloak.realm}") String realm) {
        this.keycloak = keycloak;
        this.realm = realm;
    }

    public String createUserAndAssignRole(String firstName, String lastName,
                                          String username, String password) {
        var userResource = realm().users();
//        var existsId = user.searchByUsername(username, true).stream()
//                .filter(u -> username.equalsIgnoreCase(u.getUsername()))
//                .map(UserRepresentation::getId)
//                .findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("Användaren finns redan"));
        //Skapa användare
        var userRep = new UserRepresentation();
        userRep.setUsername(username);
        userRep.setFirstName(firstName);
        userRep.setLastName(lastName);
        userRep.setEnabled(true);

        //statusar?

        try (var response = userResource.create(userRep)) {
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
            RoleRepresentation userRole = realm().roles().get("USER").toRepresentation();
            userResource.get(userId).roles().realmLevel().add(List.of(userRole));

            logger.info("Ny användare skapad");

            return userId;
        }
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
    u.update(current);
    }

    public void delete (String userId) {
        user(userId).remove();
    }

    private RealmResource realm() { return keycloak.realm(realm); }
    private UserResource user(String userId) { return realm().users().get(userId); }
}
