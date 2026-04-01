package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.AvailablePatchDto;
import com.Vivianne.Wigell_MC_Rental.dto.BookingDto;
import com.Vivianne.Wigell_MC_Rental.dto.BikeDto;
import com.Vivianne.Wigell_MC_Rental.dto.UpdateBookingDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.BookingCreateDto;
import com.Vivianne.Wigell_MC_Rental.entity.Available;
import com.Vivianne.Wigell_MC_Rental.entity.Bike;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface BookingServiceInterface {
    BookingDto findById(Long id);
    void deleteById(Long id);
    BookingDto update(Long id, BookingDto dto);
    public BookingDto create(Long customerId, Long bikeId, LocalDateTime startDate,
                             LocalDateTime endDate, Set<Available> status);
    AvailablePatchDto updatePatch(Long id, AvailablePatchDto dto);
    List<BookingDto> listBookings();
    List<Bike> listAvailableBike(LocalDateTime startDate, LocalDateTime endDate);
    BookingDto changeBooking(Long id, UpdateBookingDto dto);
    List<BookingDto> customerBooking(Long id);
}
