package com.Vivianne.Wigell_MC_Rental.dto;

import com.Vivianne.Wigell_MC_Rental.entity.Bike;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record UpdateBookingDto(LocalDateTime startDate, LocalDateTime endDate, @JsonProperty("bike") Long bikeId) {
}
