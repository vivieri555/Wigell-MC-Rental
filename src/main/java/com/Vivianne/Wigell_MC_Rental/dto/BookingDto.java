package com.Vivianne.Wigell_MC_Rental.dto;

import com.Vivianne.Wigell_MC_Rental.entity.Customer;
import com.Vivianne.Wigell_MC_Rental.entity.Bike;

import java.time.LocalDateTime;

public record BookingDto (Long id, String username, LocalDateTime startDate, LocalDateTime endDate, Long price, Bike bike, Customer customer){
}
