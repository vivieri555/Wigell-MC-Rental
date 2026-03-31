package com.Vivianne.Wigell_MC_Rental.dto;

import java.math.BigDecimal;

public record BikeDto(Long id, String brand, String model, String gearbox, String year, BigDecimal dayPrice) {
}
