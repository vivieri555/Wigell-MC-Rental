package com.Vivianne.Wigell_MC_Rental.repository;

import com.Vivianne.Wigell_MC_Rental.entity.Available;
import com.Vivianne.Wigell_MC_Rental.entity.Bike;
import com.Vivianne.Wigell_MC_Rental.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    //Kolla om mc är tillgänglig att hyra
    boolean existsByAvailableAndIdAndEndDateAfterAndStartDateBefore(
            Available available, long id, LocalDateTime now1, LocalDateTime now2);

    List<Bike> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDateTime to, LocalDateTime from);
}
