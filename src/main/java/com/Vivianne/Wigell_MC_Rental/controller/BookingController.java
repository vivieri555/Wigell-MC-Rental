package com.Vivianne.Wigell_MC_Rental.controller;

import com.Vivianne.Wigell_MC_Rental.dto.BookingDto;
import com.Vivianne.Wigell_MC_Rental.dto.UpdateBookingDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.BookingCreateDto;
import com.Vivianne.Wigell_MC_Rental.entity.Available;
import com.Vivianne.Wigell_MC_Rental.service.BikeService;
import com.Vivianne.Wigell_MC_Rental.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BikeService bikeService;

    public BookingController(BookingService bookingService, BikeService bikeService) {
        this.bookingService = bookingService;
        this.bikeService = bikeService;
    }
    //Ändra i metoder för BookingDto till BookingCreateDto  ??
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BookingDto> create(@RequestBody BookingCreateDto bookingCreateDto, Long customerId, Long bikeId, LocalDateTime startDate,
                                             LocalDateTime endDate, Set<Available> status) {
    BookingDto book = bookingService.create(customerId, bikeId, startDate,
            endDate, status);
    URI location = URI.create("/api/v1/bookings" + book.id());
    return ResponseEntity.created(location).body(book);
    }
    @PatchMapping("/{bookingId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BookingDto> changeBooking(@PathVariable Long bookingId, @RequestBody UpdateBookingDto dto) {
        BookingDto updated = bookingService.changeBooking(bookingId, dto);
        return ResponseEntity.ok(updated);
    }
    @GetMapping("?customerId={customerId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<BookingDto>> customerBooking(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(bookingService.customerBooking(customerId));
    }
    //Lista bokningar GET /api/v1/bookings
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingDto>> listBookings() {
        return ResponseEntity.ok().body(bookingService.listBookings());
    }
    @GetMapping("/{bookingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingDto> findById(@PathVariable Long bookingId) {
        return ResponseEntity.ok().body(bookingService.findById(bookingId));
    }
}
