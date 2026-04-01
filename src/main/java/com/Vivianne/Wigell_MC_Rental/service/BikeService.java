package com.Vivianne.Wigell_MC_Rental.service;

import com.Vivianne.Wigell_MC_Rental.dto_create.BikeCreateDto;
import com.Vivianne.Wigell_MC_Rental.dto.BikeDto;
import com.Vivianne.Wigell_MC_Rental.entity.Bike;
import com.Vivianne.Wigell_MC_Rental.mapper.Mapper;
import com.Vivianne.Wigell_MC_Rental.repository.BikeRepository;
import com.groupc.shared.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BikeService implements BikeServiceInterface {


    //Behöver lägga in meddelanden om avd som händer
    //ex när man raderar MC osv...

    private final BikeRepository bikeRepository;
    private final Logger logger = LoggerFactory.getLogger(BikeService.class);
    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @Override
    public BikeDto createBike(BikeCreateDto dto) {
        logger.info("Skapar ny MC");
        Bike bike = Mapper.createBike(dto);
        Bike saved = bikeRepository.save(bike);
        logger.info("MC " + bike.getBrand() + " skapad");
        return Mapper.toBikeDto(saved);
    }

    @Override
    public void delete(Long id) {
    if(!bikeRepository.existsById(id)) {
        throw new ResourceNotFoundException("Kunde inte hitta MC med id " + id);
         }
    logger.info("admin har raderat MC med id " + id);
    bikeRepository.deleteById(id);
    }
    //PUT
    @Override
    public BikeDto update(Long id, BikeDto dto) {
        Bike bike = bikeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kunde inte hitta MC med id " + id));
        bike.setBrand(dto.brand());
        bike.setModel(dto.model());
        bike.setGearbox(dto.gearbox());
        bike.setYear(dto.year());
        bikeRepository.save(bike);
        logger.info("Nu är MC " + bike.getBrand() + " uppdaterad");
        return Mapper.toBikeDto(bike);
    }

    @Override
    public List<BikeDto> listAll() {
        logger.info("Lista på alla MC");
        return bikeRepository.findAll()
                .stream()
                .filter(b -> b.getId() != null)
                .map(Mapper::toBikeDto)
                .toList();
    }

    @Override
    public BikeDto findById(Long id) {
        logger.info("Hämtar MC med id " + id);
        return bikeRepository.findById(id)
                .map(Mapper::toBikeDto)
                .orElseThrow(() -> new ResourceNotFoundException("MC hittades inte med id " + id));
    }
}
