package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto_create.AddressCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto.AddressDto;

public interface AddressServiceInterface {
    AddressDto create(AddressCreateDto addressDto, Long customerId);
    void deleteAddress(Long id, Long customerId);
}
