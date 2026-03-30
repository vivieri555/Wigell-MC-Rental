package com.Vivianne.Wigell_MC_Rental.repository;

import com.Vivianne.Wigell_MC_Rental.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeRepository extends JpaRepository<Bike, Long> {
}
