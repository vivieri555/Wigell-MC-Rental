package com.Vivianne.Wigell_MC_Rental.dto;

import com.Vivianne.Wigell_MC_Rental.entity.Address;

import java.util.List;

public record CustomerDto(Long id, String firstName, String lastName,
                          String phone, List<Address> addresses, String username) {
}
