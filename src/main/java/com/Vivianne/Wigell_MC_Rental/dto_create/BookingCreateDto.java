package com.Vivianne.Wigell_MC_Rental.dto_create;

import com.Vivianne.Wigell_MC_Rental.entity.Bike;
import com.Vivianne.Wigell_MC_Rental.entity.Customer;

import java.time.LocalDateTime;

public record BookingCreateDto(LocalDateTime startDate, LocalDateTime endDate,
                               Long price, Customer customer, Bike bike) {
}
