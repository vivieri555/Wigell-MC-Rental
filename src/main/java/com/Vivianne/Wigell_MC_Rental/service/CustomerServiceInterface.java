package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.AddressDto;
import com.Vivianne.Wigell_MC_Rental.dto.CustomerCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto.CustomerDto;

import java.util.List;

public interface CustomerServiceInterface {
    List<CustomerDto> listAll();
    CustomerDto findById(Long id);
    CustomerDto create(CustomerCreateDto dto);
    void deleteCustomer(Long id);
    CustomerDto update(CustomerDto dto, Long id);
}
