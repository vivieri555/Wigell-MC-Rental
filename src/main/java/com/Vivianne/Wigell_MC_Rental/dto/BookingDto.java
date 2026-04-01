package com.Vivianne.Wigell_MC_Rental.dto;

import com.Vivianne.Wigell_MC_Rental.entity.Available;
import com.Vivianne.Wigell_MC_Rental.entity.Customer;
import com.Vivianne.Wigell_MC_Rental.entity.Bike;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record BookingDto (Long id, LocalDateTime startDate, LocalDateTime endDate,
                          BigDecimal price, Bike bike, Customer customer, Set<Available> available){
}
