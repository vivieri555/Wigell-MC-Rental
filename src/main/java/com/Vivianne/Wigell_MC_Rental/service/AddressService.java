package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto_create.AddressCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto.AddressDto;
import com.Vivianne.Wigell_MC_Rental.entity.Address;
import com.Vivianne.Wigell_MC_Rental.mapper.Mapper;
import com.Vivianne.Wigell_MC_Rental.repository.AddressRepository;
import com.Vivianne.Wigell_MC_Rental.repository.CustomerRepository;
import com.groupc.shared.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService implements AddressServiceInterface {
    private final AddressRepository addressRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final static Logger logger = LoggerFactory.getLogger(AddressService.class);
    public AddressService(AddressRepository addressRepository, CustomerService customerService, CustomerRepository customerRepository)
    { this.addressRepository = addressRepository;
    this.customerService = customerService;
    this.customerRepository = customerRepository; }

    @Override
    @Transactional
    public AddressDto create(AddressCreateDto addressDto, Long customerId) {
        logger.info("Skapar adress på gata " + addressDto.street());
        var customer = customerService.findCustomer(customerId);
        //Lägga till addressen för en customer?

        Address address = Mapper.createAddress(addressDto);
        Address saved = addressRepository.save(address);


        customer.getAddress().add(saved);
        customerRepository.save(customer);

        return Mapper.toAddressDto(saved);
    }

    @Override
    @Transactional
    public void deleteAddress(Long addressId, Long customerId) {
        if(!addressRepository.existsById(addressId)) {
            throw new ResourceNotFoundException("Adress med id " + addressId + " existerar inte");
        }
        var customer = customerService.findCustomer(customerId);
        var address = customer.getAddress().stream()
                        .filter(a -> a.getId().equals(addressId))
                                .findFirst()
                                        .orElseThrow(() -> new ResourceNotFoundException("Inte rätt adress"));
        customer.getAddress().remove(address);
        customerRepository.save(customer);
        addressRepository.delete(address);

        logger.info("Raderar adressen med id " + addressId);
    }
}
