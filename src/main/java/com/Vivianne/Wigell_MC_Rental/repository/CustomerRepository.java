package com.Vivianne.Wigell_MC_Rental.repository;

import com.Vivianne.Wigell_MC_Rental.dto.CustomerDto;
import com.Vivianne.Wigell_MC_Rental.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByUsername(String dto);
}
