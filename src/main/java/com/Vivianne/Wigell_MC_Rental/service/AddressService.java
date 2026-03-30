package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.AddressCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto.AddressDto;
import com.Vivianne.Wigell_MC_Rental.entity.Address;
import com.Vivianne.Wigell_MC_Rental.mapper.Mapper;
import com.Vivianne.Wigell_MC_Rental.repository.AddressRepository;
import com.groupc.shared.exception.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService implements AddressServiceInterface {
    private final AddressRepository addressRepository;
    public AddressService(AddressRepository addressRepository) { this.addressRepository = addressRepository; }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public AddressDto create(AddressCreateDto addressDto) {
        Address address = Mapper.createAddress(addressDto);
        Address saved = addressRepository.save(address);
        return Mapper.toAddressDto(saved);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAddress(Long id) {
        if(!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Adress med id " + id + " existerar inte");
        }
        addressRepository.deleteById(id);
    }
}
