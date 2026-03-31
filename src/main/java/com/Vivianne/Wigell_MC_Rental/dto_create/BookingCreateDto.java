package com.Vivianne.Wigell_MC_Rental.dto_create;

import com.Vivianne.Wigell_MC_Rental.entity.Available;
import com.Vivianne.Wigell_MC_Rental.entity.Bike;
import com.Vivianne.Wigell_MC_Rental.entity.Customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record BookingCreateDto(LocalDateTime startDate, LocalDateTime endDate,
                               BigDecimal price, Customer customer, Bike bike, Set<Available> available) {
}
