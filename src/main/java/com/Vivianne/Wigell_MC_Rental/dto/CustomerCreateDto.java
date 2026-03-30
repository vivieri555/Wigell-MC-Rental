package com.Vivianne.Wigell_MC_Rental.dto;

import com.Vivianne.Wigell_MC_Rental.entity.Address;

public record CustomerCreateDto(String firstName, String lastName, String phone, Address address, String username) {
}
