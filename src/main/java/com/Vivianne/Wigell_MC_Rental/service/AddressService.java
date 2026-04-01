package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto_create.AddressCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto.AddressDto;
import com.Vivianne.Wigell_MC_Rental.entity.Address;
import com.Vivianne.Wigell_MC_Rental.mapper.Mapper;
import com.Vivianne.Wigell_MC_Rental.repository.AddressRepository;
import com.groupc.shared.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddressService implements AddressServiceInterface {
    private final AddressRepository addressRepository;
    private final static Logger logger = LoggerFactory.getLogger(AddressService.class);
    public AddressService(AddressRepository addressRepository) { this.addressRepository = addressRepository; }

    @Override
    public AddressDto create(AddressCreateDto addressDto) {
        logger.info("Skapar adress på gata " + addressDto.street());
        Address address = Mapper.createAddress(addressDto);
        Address saved = addressRepository.save(address);
        return Mapper.toAddressDto(saved);
    }

    @Override
    public void deleteAddress(Long id) {
        if(!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Adress med id " + id + " existerar inte");
        }
        logger.info("Raderar adressen med id " + id);
        addressRepository.deleteById(id);
    }
}
