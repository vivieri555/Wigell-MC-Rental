package com.Vivianne.Wigell_MC_Rental.dto;

import com.Vivianne.Wigell_MC_Rental.entity.Available;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record BookingDto (Long id, LocalDateTime startDate, LocalDateTime endDate,
                          BigDecimal priceSEK, BigDecimal totalPriceGBP, Long bikeId, Long customerId, Set<Available> available){
}
