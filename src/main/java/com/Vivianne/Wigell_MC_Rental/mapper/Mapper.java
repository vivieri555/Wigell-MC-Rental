package com.Vivianne.Wigell_MC_Rental.mapper;

import com.Vivianne.Wigell_MC_Rental.dto_create.AddressCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.BikeCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.BookingCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.CustomerCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto.*;
import com.Vivianne.Wigell_MC_Rental.entity.Address;
import com.Vivianne.Wigell_MC_Rental.entity.Bike;
import com.Vivianne.Wigell_MC_Rental.entity.Booking;
import com.Vivianne.Wigell_MC_Rental.entity.Customer;

import java.util.List;

public final class Mapper {
    public static CustomerDto toDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getFirstName(), customer.getLastName(),
                customer.getPhone(), (Address) customer.getAddress(), customer.getUsername());
    }
    public static Customer fromCreate(CustomerCreateDto createDto, String keycloakId) {
        return new Customer(createDto.firstName(), createDto.lastName(),
                createDto.phone(), (List<Address>) createDto.address(), createDto.username(), createDto.keycloakUserId());
    }
    public static Address createAddress(AddressCreateDto address) {
        return new Address(address.street(), address.city(), address.postalCode());
    }
    public static AddressDto toAddressDto(Address addressDto) {
        return new AddressDto(addressDto.getId(), addressDto.getStreet(), addressDto.getCity(), addressDto.getPostalCode());
    }
    public static BikeDto toBikeDto(Bike bike) {
        return new BikeDto(bike.getId(), bike.getBrand(), bike.getModel(), bike.getGearbox(), bike.getYear(), bike.getDayPrice());
    }
    public static Bike createBike (BikeCreateDto bikeDto) {
        return new Bike(bikeDto.brand(), bikeDto.model(), bikeDto.gearbox(), bikeDto.year(), bikeDto.dayPrice());
    }
    public static BookingDto toBookingDto(Booking booking) {
        return new BookingDto(booking.getId(), booking.getStartDate(), booking.getEndDate(), booking.getPrice(),
                booking.getBike(), booking.getCustomer(), booking.getAvailable());
    }
    public static Booking createBooking(BookingCreateDto bDto) {
        return new Booking(bDto.startDate(), bDto.endDate(), bDto.price(), bDto.bike(), bDto.customer(), bDto.available());
    }
}
