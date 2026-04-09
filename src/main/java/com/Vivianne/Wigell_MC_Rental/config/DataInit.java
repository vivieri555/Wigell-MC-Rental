package com.Vivianne.Wigell_MC_Rental.config;

import com.Vivianne.Wigell_MC_Rental.dto.BookingDto;
import com.Vivianne.Wigell_MC_Rental.entity.*;
import com.Vivianne.Wigell_MC_Rental.repository.AddressRepository;
import com.Vivianne.Wigell_MC_Rental.repository.BikeRepository;
import com.Vivianne.Wigell_MC_Rental.repository.BookingRepository;
import com.Vivianne.Wigell_MC_Rental.repository.CustomerRepository;
import com.Vivianne.Wigell_MC_Rental.service.BookingService;
import com.Vivianne.Wigell_MC_Rental.service.KeycloakUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class DataInit {

    private static Logger logger = LoggerFactory.getLogger(DataInit.class);

    @Bean
    CommandLineRunner initData(CustomerRepository customerRepo,
                               AddressRepository addressRepo, KeycloakUserService keycloakUserService,
                               BikeRepository bikeRepo, BookingService bookingService, BookingRepository bookingRepo) {
        return args -> {
            if (customerRepo.count() == 0)
            {

                logger.info("Skapa nya användare");

                Address address1 = new Address("Stockholmsvägen 2", "Stockholm", "11724");
                List<Address> address1List = new ArrayList<>();
                addressRepo.save(address1);
                address1List.add(address1);
                Address simtuna3 = new Address("Simtuna 3", "Enköping", "74951");
                List<Address> simtuna3List = new ArrayList<>();
                simtuna3List.add(simtuna3);
                Address kungsgatan54 = new Address("Kungsgatan 54", "Sundsvall", "84555");
                List<Address> kungsgatan54List = new ArrayList<>();
                kungsgatan54List.add(kungsgatan54);
                Address gasvagen109 = new Address("Gasvägen 109", "Gävle", "74821");
                List<Address> gasvagen109List = new ArrayList<>();
                gasvagen109List.add(gasvagen109);

                try {
                String admin = keycloakUserService.createUserKeycloak("Vivianne", "Eriksson", "vivi@email.se", "password", "ADMIN");
                    Customer customer1 = new Customer("Vivianne", "Eriksson", "0707000000", address1List
                            , "vivi@email.se", admin);

                String alexandra1 = keycloakUserService.createUserKeycloak("Alexandra", "Andersson", "alexandra@live.com", "password", "USER");
                Customer customer2 = new Customer("Alexandra", "Andersson", "0789634365", simtuna3List, "alexandra@live.com", alexandra1);

                String pelle3 = keycloakUserService.createUserKeycloak("Pelle", "Nordin", "pelle.storhammar@telia.com", "password", "USER");
                Customer customer3 = new Customer("Pelle", "Nordin", "0765437568", kungsgatan54List, "pelle.storhammar@telia.com", pelle3);

                String roger = keycloakUserService.createUserKeycloak("Roger", "Nordin","nordin@hotmail.com", "password", "USER");
                Customer customer4 = new Customer("Roger", "Nordin", "085453486", kungsgatan54List, "nordin@hotmail.com", roger);

                String anders = keycloakUserService.createUserKeycloak("Anders", "Hammar", "as@live.com", "password", "USER");
                Customer customer5 = new Customer("Anders", "Hammar", "0763355899", gasvagen109List, "as@live.com", anders);
                    customerRepo.saveAll(List.of(customer1, customer2, customer3, customer4, customer5));
                    customerRepo.flush();
                logger.info("Lagt till nya medlemmar");


                    Bike bike1 = new Bike("Kawasaki", "Turbo22", "Manuell", "2025", BigDecimal.valueOf(1255));
                    Bike bike2 = new Bike("BMW", "i9", "Manuell", "2026", BigDecimal.valueOf(1300));
                    Bike bike3 = new Bike("Yamaha", "MT", "Automat", "2024", BigDecimal.valueOf(1500));
                    Bike bike4 = new Bike("Harley-Davidson", "Iron 883", "Manuell", "2023", BigDecimal.valueOf(1100));
                    Bike bike5 = new Bike("Triumph tiger", "800 XC", "Manuell", "2026", BigDecimal.valueOf(1450));
                    bikeRepo.saveAll(List.of(bike1, bike2, bike3, bike4, bike5));
                logger.info("Sparat MC i db");

                BookingDto booking1 = bookingService.create(customer5.getId(), bike5.getId(), LocalDateTime.now(),
                        LocalDateTime.of(2026, 4, 30, 12, 30), Set.of(Available.CONFIRMED));
                BookingDto booking2 = bookingService.create(customer1.getId(),bike4.getId(), LocalDateTime.of(2026, 4, 9, 12, 1),
                        LocalDateTime.now(), Set.of(Available.CONFIRMED));
               logger.info("Bokningar sparade " + booking1 + ", " + booking2); }
                catch (Exception e) {
                    logger.error(e.getMessage());
                }
            } else {
                logger.info("Finns redan data");
            }
        };
    }
}
