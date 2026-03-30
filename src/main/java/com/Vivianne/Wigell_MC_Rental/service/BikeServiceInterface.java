package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.BikeCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto.BikeDto;

import java.util.List;

public interface BikeServiceInterface {
    BikeDto createBike(BikeCreateDto dto);
    void delete(Long id);
    BikeDto update(Long id, BikeDto dto);
    List<BikeDto> listAll();
    BikeDto findById(Long id);
}
