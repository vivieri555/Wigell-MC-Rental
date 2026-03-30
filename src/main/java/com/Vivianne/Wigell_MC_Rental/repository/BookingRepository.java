package com.Vivianne.Wigell_MC_Rental.repository;

import com.Vivianne.Wigell_MC_Rental.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
