package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.AvailablePatchDto;
import com.Vivianne.Wigell_MC_Rental.dto.BikeDto;
import com.Vivianne.Wigell_MC_Rental.dto.BookingDto;
import com.Vivianne.Wigell_MC_Rental.dto.UpdateBookingDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.BookingCreateDto;
import com.Vivianne.Wigell_MC_Rental.entity.Available;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public AvailablePatchDto updatePatch(Long id, AvailablePatchDto dto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Bokning hittades inte med id: " + id));

        if (dto.available() != null) {
            booking.setAvailable(dto.available());
        }
        Booking saved = bookingRepository.save(booking);
        return new AvailablePatchDto(saved.getAvailable());
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
    public List<Bike> listAvailableBike(LocalDateTime startDate, LocalDateTime endDate) {
       return bookingRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(startDate, endDate);
    }

    //Hyr motorcykel POST /api/v1/bookings
    //customer, ledig bike, lägga in i Booking
    public BookingDto rentBike(Long customerId, Long bikeId, LocalDateTime startDate,
                               LocalDateTime endDate, Set<Available> status) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Kund kunde inte hittas med id " + customerId));

        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new ResourceNotFoundException("Hittade inte MC med id " + bikeId));

        boolean isBusy = bookingRepository.existsByAvailableAndIdAndEndDateAfterAndStartDateBefore(Available.AVAILABLE, bikeId, startDate, endDate);
        if (isBusy) {
            throw new ResourceNotFoundException("MC är inte tillgänglig dessa datum");
        }

        long days = dateDiff(startDate, endDate);
        if (days <= 0) days = 1;
        BigDecimal totalPrice = bike.getDayPrice().multiply(BigDecimal.valueOf(days));

        Booking booking =  new Booking(startDate, endDate, totalPrice, bike, customer, status);
                Booking saved = bookingRepository.save(booking);
        return Mapper.toBookingDto(saved);

    }

    //Uppdatera bokning PATCH /api/v1/bookings/{bookingId} (tillåtna fält: motorcykel, datum)
    public BookingDto changeBooking(Long id, UpdateBookingDto dto) {
       var booking = bookingRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Hittade inte bokning med id " + id));
       if(dto.startDate() != null) {
           booking.setStartDate(dto.startDate());
       }
       if (dto.endDate() != null) {
           booking.setEndDate(dto.endDate());
       }
       if (dto.bike() != null) {
           booking.setBike(dto.bike());
       }
       var saved = bookingRepository.save(booking);
       return Mapper.toBookingDto(saved);
    }

    //Lista bokningar GET /api/v1/bookings?customerId={customerId}
    public List<BookingDto> customerBooking(Long id) {
       List<Booking> bookings = bookingRepository.findByCustomerId(id);
       return bookings.stream()
               .map(Mapper::toBookingDto)
               .collect(Collectors.toList());
    }

    public LocalDateTime rentalDate(String rentalDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime bookingDate;
        try {
            bookingDate = LocalDateTime.parse(rentalDate, formatter);
            System.out.println("Påbörjar uthyrningen datumet " + bookingDate);
        }
        catch (Exception e) {
            throw new ResourceNotFoundException("Ogiltigt uthyrningsdatum, fyll i åååå-mm-dd");
        }
        return bookingDate;
    }
    public LocalDateTime returnDate(String returnDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime returnD;
        try {
            returnD = LocalDateTime.parse(returnDate, formatter);
            System.out.println("Återlämningsdatum: " + returnD);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Ogiltigt returdatum, fyll i åååå-mm-dd");
        }
        return returnD;
    }
    public long dateDiff(LocalDateTime rentalDate, LocalDateTime returnDate) {
        return rentalDate.until(returnDate, ChronoUnit.DAYS);
    }
}
