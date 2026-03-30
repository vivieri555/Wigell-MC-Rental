package com.Vivianne.Wigell_MC_Rental.mapper;

import com.Vivianne.Wigell_MC_Rental.dto.*;
import com.Vivianne.Wigell_MC_Rental.entity.Address;
import com.Vivianne.Wigell_MC_Rental.entity.Bike;
import com.Vivianne.Wigell_MC_Rental.entity.Customer;

public final class Mapper {
    public static CustomerDto toDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getFirstName(), customer.getLastName(),
                customer.getPhone(), customer.getAddress(), customer.getUsername());
    }
    public static Customer fromCreate(CustomerCreateDto createDto) {
        return new Customer(createDto.firstName(), createDto.lastName(),
                createDto.phone(), createDto.address(), createDto.username());
    }
    public static Address createAddress(AddressCreateDto address) {
        return new Address(address.street(), address.city(), address.postalCode());
    }
    public static AddressDto toAddressDto(Address addressDto) {
        return new AddressDto(addressDto.getId(), addressDto.getStreet(), addressDto.getCity(), addressDto.getPostalCode());
    }
    public static BikeDto toBikeDto(Bike bike) {
        return new BikeDto(bike.getId(), bike.getBrand(), bike.getModel(), bike.getGearbox(), bike.getYear());
    }
    public static Bike createBike (BikeCreateDto bikeDto) {
        return new Bike(bikeDto.brand(), bikeDto.model(), bikeDto.gearbox(), bikeDto.year());
    }
}
