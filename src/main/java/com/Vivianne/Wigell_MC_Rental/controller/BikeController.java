package com.Vivianne.Wigell_MC_Rental.controller;

import com.Vivianne.Wigell_MC_Rental.dto.BikeDto;
import com.Vivianne.Wigell_MC_Rental.dto_create.BikeCreateDto;
import com.Vivianne.Wigell_MC_Rental.service.BikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bikes")
public class BikeController {

    private final BikeService bikeService;
    public BikeController(BikeService bikeService) { this.bikeService = bikeService;}

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BikeDto> create(@RequestBody BikeCreateDto dto) {
        BikeDto bike = bikeService.createBike(dto);
        URI location = URI.create("/api/v1/bikes" + bike.id());
        return ResponseEntity.created(location).body(bike);
    }

    @DeleteMapping("/{bikeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BikeDto> delete(@PathVariable Long bikeId) {
        bikeService.delete(bikeId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{bikeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BikeDto> update(@PathVariable Long bikeId, @RequestBody BikeDto dto) {
        return ResponseEntity.ok(bikeService.update(bikeId, dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BikeDto>> listAll() {
        return ResponseEntity.ok(bikeService.listAll());
    }

    @GetMapping("/{bikeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BikeDto> findById(@PathVariable Long bikeId) {
        return ResponseEntity.ok(bikeService.findById(bikeId));
    }
}
