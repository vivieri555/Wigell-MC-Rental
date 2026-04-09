package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.UpdateUserDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.CustomerCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto.CustomerDto;
import com.Vivianne.Wigell_MC_Rental.entity.Customer;
import com.Vivianne.Wigell_MC_Rental.mapper.Mapper;
import com.Vivianne.Wigell_MC_Rental.repository.CustomerRepository;
import com.groupc.shared.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements CustomerServiceInterface {

    private final CustomerRepository customerRepository;
    private final KeycloakUserService keycloakUserService;
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository,
                           KeycloakUserService keycloakUserService) {
        this.customerRepository = customerRepository;
        this.keycloakUserService = keycloakUserService;
    }

    @Override
    public List<CustomerDto> listAll() {
        logger.info("Lista alla kunder");
        return customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getId() != null)
                .map(Mapper::toDto)
                .toList();
    }

    @Override
    public CustomerDto findById(Long id) {
        logger.info("Hitta kund med id " + id);
        return customerRepository.findById(id)
                .map(Mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Medlem hittades inte med id " + id));
    }

    @Override
    public CustomerDto create(CustomerCreateDto dto) {
        if(customerRepository.existsByUsername(dto.username())) {
            throw new RuntimeException("Användarnamnet finns redan");
        }

        String keycloakId = keycloakUserService.createUserAndAssignRole(
                dto.firstName(),
                dto.lastName(),
                dto.username(),
                dto.password(),
                "USER"
        );

        Customer customer = Mapper.fromCreate(dto, keycloakId);

        logger.info("Kund skapad " + customer.getFirstName() +" " + customer.getLastName());
        return Mapper.toDto(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Long id) {
    if(!customerRepository.existsById(id)) {
    throw new RuntimeException("Kund med id " + id + " existerar inte");
        }
    keycloakUserService.delete(findCustomer(id).getKeycloakUserId());

    logger.info("Kund raderas med id " + id);
    customerRepository.deleteById(id);
    }

    //PUT metoden
    @Override
    public CustomerDto update(CustomerDto dto, Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kund hittades inte med id " + id));

        boolean firstNameChanged = dto.firstName() != null && !dto.firstName().equals(customer.getFirstName());
        boolean lastNameChanged = dto.lastName() != null && !dto.lastName().equals(customer.getLastName());

        if ( customer.getKeycloakUserId() != null && firstNameChanged || lastNameChanged) {
            keycloakUserService.updateUserProfile(
                    customer.getKeycloakUserId(),
                    new UpdateUserDto(
                            firstNameChanged ? dto.firstName() : null,
                            lastNameChanged ? dto.lastName() : null
                    )
            );
        }
        customer.setFirstName(dto.firstName());
        customer.setLastName(dto.lastName());
        customer.setPhone(dto.phone());
        customer.setAddress(dto.addresses());
        customer.setUsername(dto.username());

        customerRepository.save(customer);
        logger.info("Kunden " + customer.getFirstName() +" " + customer.getLastName() + " är nu uppdaterad");
        return Mapper.toDto(customer);
    }
    public Customer findCustomer(Long customerId) {
        logger.debug("Hitta kund");
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Hittar inte kund"));
    }
}
