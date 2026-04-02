package com.Vivianne.Wigell_MC_Rental.dto_create;

import org.springframework.transaction.annotation.Transactional;

@Override
@Transactional
public CreateUserToCustomer create(CustomerWithUserRequestDto request) {

//    String keycloakId = keycloakUserService.createUserKeycloak(
//            request.email(),
//            request.username(),
//            request.password(),
//            request.firstName(),
//            request.lastName()
//    );
//
//    var customer = mapper.toEntity(request, keycloakId);
//    var saved = customerRepo.save(customer);
//    return mapper.toResponse(saved);
}
