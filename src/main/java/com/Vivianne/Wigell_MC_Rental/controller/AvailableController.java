package com.Vivianne.Wigell_MC_Rental.controller;

import com.Vivianne.Wigell_MC_Rental.dto.AvailablePatchDto;
import com.Vivianne.Wigell_MC_Rental.entity.Bike;
import com.Vivianne.Wigell_MC_Rental.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AvailableController {

    private final BookingService bookingService;
    public AvailableController(BookingService bookingService) { this.bookingService = bookingService; }

    //Lista lediga motorcyklar
    @GetMapping("/availability?from={YYYY-MM-DD}&to={YYYY-MM-DD}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Bike>> listAvailableBike(LocalDateTime startDate, LocalDateTime endDate) {
        return ResponseEntity.ok(bookingService.listAvailableBike(startDate, endDate));
    }
}
