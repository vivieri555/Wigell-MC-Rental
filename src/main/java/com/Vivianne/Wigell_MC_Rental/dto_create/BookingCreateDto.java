package com.Vivianne.Wigell_MC_Rental.dto_create;

import com.Vivianne.Wigell_MC_Rental.entity.Available;
import com.Vivianne.Wigell_MC_Rental.entity.Bike;
import com.Vivianne.Wigell_MC_Rental.entity.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Set;

public record BookingCreateDto(LocalDateTime startDate, LocalDateTime endDate,
                               @JsonProperty ("customer") Long customerId, @JsonProperty ("bike") Long bikeId, Set<Available> available) {
}
