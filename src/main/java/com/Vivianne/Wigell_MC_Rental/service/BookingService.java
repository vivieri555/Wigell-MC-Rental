package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.AvailablePatchDto;
import com.Vivianne.Wigell_MC_Rental.dto.BookingDto;
import com.Vivianne.Wigell_MC_Rental.dto.UpdateBookingDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.BookingCreateDto;
import com.Vivianne.Wigell_MC_Rental.entity.Booking;
import com.Vivianne.Wigell_MC_Rental.mapper.Mapper;
import com.Vivianne.Wigell_MC_Rental.repository.BookingRepository;
import com.groupc.shared.exception.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService implements BookingServiceInterface{
   private final BookingRepository bookingRepository;

   public BookingService(BookingRepository bookingRepository) {
       this.bookingRepository = bookingRepository;
   }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public BookingDto findById(Long id) {
        return bookingRepository.findById(id)
                .map(Mapper::toBookingDto)
                .orElseThrow(() -> new ResourceNotFoundException("Bokning hittades inte med id " + id));
    }
    //User ska kunna boka
    @Override
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public BookingDto create(BookingCreateDto bookingDto) {
        Booking booking = Mapper.createBooking(bookingDto);
        Booking saved = bookingRepository.save(booking);
        return Mapper.toBookingDto(saved);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id) {
    if(!bookingRepository.existsById(id)) {
        throw new ResourceNotFoundException("Bokning hittades inte med id: " + id);
        }
    bookingRepository.deleteById(id);
    }
    //PUT
    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public BookingDto update(Long id, BookingDto b) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bokning hittades inte med id: " + id));
        booking.setStartDate(b.startDate());
        booking.setEndDate(b.endDate());
        booking.setPrice(b.price());
        booking.setBike(b.bike());
        booking.setCustomer(b.customer());
        bookingRepository.save(booking);
        return Mapper.toBookingDto(booking);
    }
    //Patch admin
    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public BookingDto updatePatch(Long id, AvailablePatchDto dto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Bokning hittades inte med id: " + id));
        if(dto.available() != null) {
            booking.setAvailable(dto);
        }
        booking saved = bookingRepository.save(booking);
        return new AvailablePatchDto(saved);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookingDto> listBookings(Long id) {
        return bookingRepository.findAll()
                .stream()
                .filter(b -> b.getId() != null)
                .map(Mapper::toBookingDto)
                .toList();
    }
}
