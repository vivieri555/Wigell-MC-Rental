package com.Vivianne.Wigell_MC_Rental.dto_create;

import com.Vivianne.Wigell_MC_Rental.entity.Address;

public record CustomerCreateDto(String firstName, String lastName, String phone,
                                Address address, String username, String password, String keycloakUserId) {
}
