package com.Vivianne.Wigell_MC_Rental.config;

import com.Vivianne.Wigell_MC_Rental.entity.Address;
import com.Vivianne.Wigell_MC_Rental.entity.Bike;
import com.Vivianne.Wigell_MC_Rental.entity.Customer;
import com.Vivianne.Wigell_MC_Rental.repository.AddressRepository;
import com.Vivianne.Wigell_MC_Rental.repository.BikeRepository;
import com.Vivianne.Wigell_MC_Rental.repository.CustomerRepository;
import com.Vivianne.Wigell_MC_Rental.service.KeycloakUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInit {

    @Bean
    CommandLineRunner initData(CustomerRepository customerRepo,
                               AddressRepository addressRepo, KeycloakUserService keycloakUserService,
                               BikeRepository bikeRepo) {
        return args -> {
//            if (customerRepo.count == 0)
            {
                //Skapa keycloak admin användare? skapas bara i keycloak

                Address address1 = new Address("Stockholmsvägen 2", "Stockholm", "11724");
                List<Address> address1List = new ArrayList<>();
                addressRepo.save(address1);
                address1List.add(address1);


                Customer customer1 = new Customer("Vivianne", "Eriksson", "0707000000", address1List
                        , "vivi@email.se");
                customerRepo.save(customer1);

                Bike bike1 = new Bike("Kawasaki", "Turbo22", "Manuell", "2025", BigDecimal.valueOf(1255));
                bikeRepo.save(bike1);

            }
        };
    }
}
