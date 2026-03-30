package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.AddressCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto.AddressDto;

public interface AddressServiceInterface {
    AddressDto create(AddressCreateDto addressDto);
    void deleteAddress(Long id);
}
