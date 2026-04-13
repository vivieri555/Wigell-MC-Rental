package com.Vivianne.Wigell_MC_Rental.controller;

import com.Vivianne.Wigell_MC_Rental.dto.AvailablePatchDto;
import com.Vivianne.Wigell_MC_Rental.dto.BookingDto;
import com.Vivianne.Wigell_MC_Rental.dto.UpdateBookingDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.BookingCreateDto;
import com.Vivianne.Wigell_MC_Rental.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BookingDto> create(@RequestBody BookingCreateDto dto) {
    BookingDto book = bookingService.create(dto.customerId(), dto.bikeId(), dto.startDate(),
            dto.endDate(), dto.available());
    URI location = URI.create("/api/v1/bookings/" + book.id());
    return ResponseEntity.created(location).body(book);
    }
    //Uppdatera bokning PATCH /api/v1/bookings/{bookingId} (tillåtna fält: motorcykel, datum)
    @PatchMapping("/{bookingId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BookingDto> changeBooking(@PathVariable Long bookingId, @RequestBody UpdateBookingDto dto) {
        BookingDto updated = bookingService.changeBooking(bookingId, dto);
        return ResponseEntity.ok(updated);
    }
    //Lista bokningar GET /api/v1/bookings
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingDto>> listBookings() {
        return ResponseEntity.ok().body(bookingService.listBookings());
    }
    //Lista bokningar GET /api/v1/bookings?customerId={customerId}
    @GetMapping(params = "customerId")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<BookingDto>> customerBooking(@RequestParam Long customerId) {
        return ResponseEntity.ok().body(bookingService.customerBooking(customerId));
    }
    @GetMapping("/{bookingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingDto> findById(@PathVariable Long bookingId) {
        return ResponseEntity.ok().body(bookingService.findById(bookingId));
    }
    @PutMapping("/{bookingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingDto> update(@PathVariable Long bookingId, @RequestBody BookingDto dto) {
        return ResponseEntity.ok().body(bookingService.update(bookingId, dto));
    }
    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long bookingId) {
        bookingService.deleteById(bookingId);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping(value = "/{bookingId}", params = "type=available")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AvailablePatchDto> updatePatch(@PathVariable Long bookingId, AvailablePatchDto dto) {
        return ResponseEntity.ok(bookingService.updatePatch(bookingId, dto));
    }
}
