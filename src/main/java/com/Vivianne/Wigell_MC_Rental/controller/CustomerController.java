package com.Vivianne.Wigell_MC_Rental.controller;

import com.Vivianne.Wigell_MC_Rental.dto.AddressDto;
import com.Vivianne.Wigell_MC_Rental.dto.CustomerDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.AddressCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.CustomerCreateDto;
import com.Vivianne.Wigell_MC_Rental.service.AddressService;
import com.Vivianne.Wigell_MC_Rental.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@PreAuthorize("hasRole('ADMIN')")
public class CustomerController {

    private final CustomerService customerService;
    private final AddressService addressService;
    public CustomerController(CustomerService customerService, AddressService addressService) {
        this.customerService = customerService;
        this.addressService = addressService; }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getCustomers() { return ResponseEntity.ok(customerService.listAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable Long id) { return ResponseEntity.ok(customerService.findById(id)); }

    @PostMapping
    public ResponseEntity<CustomerDto> create(@RequestBody CustomerCreateDto customerDto) {
        CustomerDto customer = customerService.create(customerDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{customerId}")
                .buildAndExpand(customer.id()).toUri();
        return ResponseEntity.created(location).body(customer);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> delete(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDto> update(@PathVariable Long customerId, @RequestBody @Valid CustomerDto dto) {
        return ResponseEntity.ok(customerService.update(dto, customerId));
    }

    //Behöver göra om metoderna för adresserna, så de kan hantera customerId
    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<AddressDto> createAddress(@PathVariable Long customerId, @RequestBody AddressCreateDto addressDto) {
        AddressDto address = addressService.create(addressDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{customerId}")
                .buildAndExpand(address.id()).toUri();
        return ResponseEntity.created(location).body(address);
    }
    @DeleteMapping("/{customerId}/addresses/{addressId}")
    public ResponseEntity<AddressDto> deleteAddress(@PathVariable Long customerId, @PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }
}
