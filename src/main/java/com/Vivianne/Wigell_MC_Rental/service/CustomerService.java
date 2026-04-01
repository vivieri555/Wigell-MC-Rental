package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto_create.CustomerCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto.CustomerDto;
import com.Vivianne.Wigell_MC_Rental.entity.Customer;
import com.Vivianne.Wigell_MC_Rental.mapper.Mapper;
import com.Vivianne.Wigell_MC_Rental.repository.CustomerRepository;
import com.groupc.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements CustomerServiceInterface {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDto> listAll() {
        return customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getId() != null)
                .map(Mapper::toDto)
                .toList();
    }

    @Override
    public CustomerDto findById(Long id) {
        return customerRepository.findById(id)
                .map(Mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Medlem hittades inte med id " + id));
    }

    @Override
    public CustomerDto create(CustomerCreateDto dto) {
        if(customerRepository.existsByUsername(dto.username())) {
            throw new RuntimeException("Användarnamnet finns redan");
        }
        Customer customer = Mapper.fromCreate(dto);
        return Mapper.toDto(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Long id) {
    if(!customerRepository.existsById(id)) {
    throw new RuntimeException("Kund med id " + id + " existerar inte");
        }
    customerRepository.deleteById(id);
    }

    //PUT metoden
    @Override
    public CustomerDto update(CustomerDto dto, Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kunde hittades inte med id " + id));

        customer.setFirstName(dto.firstName());
        customer.setLastName(dto.lastName());
        customer.setPhone(dto.phone());
        customer.setAddress(dto.address());
        customer.setUsername(dto.username());

        customerRepository.save(customer);
        return Mapper.toDto(customer);
    }
}
