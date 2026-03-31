package com.Vivianne.Wigell_MC_Rental.dto_create;

import java.math.BigDecimal;

public record BikeCreateDto(String brand, String model, String gearbox, String year, BigDecimal dayPrice) {
}
