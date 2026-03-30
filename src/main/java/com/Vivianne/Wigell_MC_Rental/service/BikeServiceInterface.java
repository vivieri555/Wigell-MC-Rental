package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto.BikeDto;

import java.util.List;

public interface BikeServiceInterface {
    BikeDto create(BikeDto dto);
    void delete(BikeDto dto);
    BikeDto update(Long id);
    List<BikeDto> listAll();
    List<BikeDto> findById(Long id);
}
