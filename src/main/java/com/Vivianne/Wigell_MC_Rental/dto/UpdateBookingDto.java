package com.Vivianne.Wigell_MC_Rental.dto;

import com.Vivianne.Wigell_MC_Rental.entity.Bike;

import java.time.LocalDateTime;

public record UpdateBookingDto(LocalDateTime startDate, LocalDateTime endDate, Bike bike) {
}
