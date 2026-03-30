package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.BookingDto;
import com.Vivianne.Wigell_MC_Rental.dto.BikeDto;
import com.Vivianne.Wigell_MC_Rental.dto.UpdateBookingDto;

import java.util.List;

public interface BookingServiceInterface {
    List<BikeDto> findAll();
    List<BookingDto> findById(Long id);
    BookingDto create(BookingDto bookingDto);
    void deleteById(Long id);
    BookingDto update(Long id);
    //returnera bokning patch, tillåtna fält status ?
    BookingDto updatePatch(UpdateBookingDto dto);
    List<BookingDto> listBookings(Long id);
}
