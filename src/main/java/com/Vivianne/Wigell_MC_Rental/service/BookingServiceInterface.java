package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.AvailablePatchDto;
import com.Vivianne.Wigell_MC_Rental.dto.BookingDto;
import com.Vivianne.Wigell_MC_Rental.dto.BikeDto;
import com.Vivianne.Wigell_MC_Rental.dto.UpdateBookingDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.BookingCreateDto;

import java.util.List;

public interface BookingServiceInterface {
    BookingDto findById(Long id);
    BookingDto create(BookingCreateDto bookingDto);
    void deleteById(Long id);
    BookingDto update(Long id, BookingDto dto);
    AvailablePatchDto updatePatch(Long id, AvailablePatchDto dto);
    List<BookingDto> listBookings(Long id);
}
