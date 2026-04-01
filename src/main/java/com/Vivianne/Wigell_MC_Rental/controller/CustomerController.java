package com.Vivianne.Wigell_MC_Rental.controller;

import com.Vivianne.Wigell_MC_Rental.dto.CustomerDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.CustomerCreateDto;
import com.Vivianne.Wigell_MC_Rental.entity.Customer;
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
    public CustomerController(CustomerService customerService) { this.customerService = customerService; }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getCustomers() { return ResponseEntity.ok(customerService.listAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable Long id) { return ResponseEntity.ok(customerService.findById(id)); }

    @PostMapping
    public ResponseEntity<CustomerDto> create(@RequestBody CustomerCreateDto customerDto) {
        CustomerDto customer = customerService.create(customerDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(customer.id()).toUri();
        return ResponseEntity.created(location).body(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@PathVariable Long id, @RequestBody @Valid CustomerDto dto) {
        return ResponseEntity.ok(customerService.update(dto, id));
    }
}
