package com.Vivianne.Wigell_MC_Rental.security;

import com.Vivianne.Wigell_MC_Rental.repository.CustomerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component("ownership")
public class Ownership {

    private final CustomerRepository customerRepository;

    public Ownership(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public boolean customerIsUser(Authentication authentication, Long customerId) {
        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) return false;
        String sub = jwtAuth.getToken().getClaimAsString("sub");
        if (sub == null) return false;
        return customerRepository.findById(customerId)
                .map(c -> sub.equals(c.getUsername()))
                .orElse(false);
    }
}
