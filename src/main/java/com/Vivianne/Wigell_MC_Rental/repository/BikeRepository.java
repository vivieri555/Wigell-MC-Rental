package com.Vivianne.Wigell_MC_Rental.repository;

import com.Vivianne.Wigell_MC_Rental.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BikeRepository extends JpaRepository<Bike, Long> {

    @Query("SELECT b FROM Bike b WHERE b.id NOT IN (" +
            "SELECT bo.bike.id FROM Booking bo " +
            "WHERE bo.startDate < :to AND bo.endDate > :from)")
    List<Bike> findAvailableBikes(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
