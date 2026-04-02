package com.Vivianne.Wigell_MC_Rental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.Vivianne.Wigell_MC_Rental", "com.groupc.shared"})
public class WigellMcRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(WigellMcRentalApplication.class, args);
	}

}
