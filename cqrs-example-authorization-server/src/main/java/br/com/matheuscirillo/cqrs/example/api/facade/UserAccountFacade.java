package br.com.matheuscirillo.cqrs.example.api.facade;

import br.com.matheuscirillo.cqrs.example.api.dto.AuthenticationRequest;
import br.com.matheuscirillo.cqrs.example.api.dto.AuthenticationResponse;
import br.com.matheuscirillo.cqrs.example.api.dto.RegistrationRequest;

public interface UserAccountFacade {

    Integer register(RegistrationRequest request);
    
    AuthenticationResponse authenticate(AuthenticationRequest request);

}
