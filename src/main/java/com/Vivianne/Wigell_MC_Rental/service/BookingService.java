package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.AvailablePatchDto;
import com.Vivianne.Wigell_MC_Rental.dto.BikeDto;
import com.Vivianne.Wigell_MC_Rental.dto.BookingDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.BookingCreateDto;
import com.Vivianne.Wigell_MC_Rental.entity.Bike;
import com.Vivianne.Wigell_MC_Rental.entity.Booking;
import com.Vivianne.Wigell_MC_Rental.entity.Customer;
import com.Vivianne.Wigell_MC_Rental.mapper.Mapper;
import com.Vivianne.Wigell_MC_Rental.repository.BikeRepository;
import com.Vivianne.Wigell_MC_Rental.repository.BookingRepository;
import com.Vivianne.Wigell_MC_Rental.repository.CustomerRepository;
import com.groupc.shared.exception.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService implements BookingServiceInterface{
   private final BookingRepository bookingRepository;
   private final CustomerRepository customerRepository;
   private final BikeRepository bikeRepository;

   public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository,
                         BikeRepository bikeRepository) {
       this.bookingRepository = bookingRepository;
       this.customerRepository = customerRepository;
       this.bikeRepository = bikeRepository;
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

        if (dto.available() != null) {
            booking.setAvailable(dto.available());
        }
        Booking saved = bookingRepository.save(booking);
       // return new AvailablePatchDto(saved.getAvailable());
        return new BookingDto(saved.getAvailable(), saved.getBike());
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
    //Lista lediga motorcyklar GET /api/v1/availability?from={YYYY-MM-DD}&to={YYYY-MM-DD}
    //GetAvailableBikes
    List<Bike> avBike = bikeRepository.findByAvailableTrue();


    //Hyr motorcykel POST /api/v1/bookings
    //customer, ledig bike, lägga in i Booking
    public BikeDto rentBike(BikeDto dto, Long id, Long bikeId) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kund kunde inte hittas med id " + id));

        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new ResourceNotFoundException("Hittade inte MC med id " + bikeId));

    //behöver startdate, enddate, pris. kolla att bike är ledig det datumet?

    }

    //Uppdatera bokning PATCH /api/v1/bookings/{bookingId} (tillåtna fält: motorcykel, datum)

    //Lista bokningar GET /api/v1/bookings?customerId={customerId}
}
