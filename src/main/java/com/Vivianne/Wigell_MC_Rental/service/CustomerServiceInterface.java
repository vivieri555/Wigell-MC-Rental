package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.AddressDto;
import com.Vivianne.Wigell_MC_Rental.dto.CustomerDto;

import java.util.List;

public interface CustomerServiceInterface {
    List<CustomerDto> listAll();
    List<CustomerDto> findById(Long id);
    CustomerDto create(CustomerDto dto);
    void deleteById(Long id);
    CustomerDto update(Long id);
    AddressDto create(AddressDto addressDto);
    void delete(AddressDto addressDto);
}
