package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto_create.CustomerCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto.CustomerDto;

import java.util.List;

public interface CustomerServiceInterface {
    List<CustomerDto> listAll();
    CustomerDto findById(Long id);
    CustomerDto create(CustomerCreateDto dto);
    void deleteCustomer(Long id);
    CustomerDto update(CustomerDto dto, Long id);
}
