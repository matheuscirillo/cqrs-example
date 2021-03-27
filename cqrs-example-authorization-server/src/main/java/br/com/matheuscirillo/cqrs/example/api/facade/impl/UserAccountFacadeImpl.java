package br.com.matheuscirillo.cqrs.example.api.facade.impl;

import br.com.matheuscirillo.cqrs.example.api.dto.AuthenticationRequest;
import br.com.matheuscirillo.cqrs.example.api.dto.AuthenticationResponse;
import br.com.matheuscirillo.cqrs.example.api.dto.RegistrationRequest;
import br.com.matheuscirillo.cqrs.example.api.exception.ApiException;
import br.com.matheuscirillo.cqrs.example.api.exception.ErrorType;
import br.com.matheuscirillo.cqrs.example.api.facade.UserAccountFacade;
import br.com.matheuscirillo.cqrs.example.domain.entity.UserAccount;
import br.com.matheuscirillo.cqrs.example.domain.exception.UnauthorizedException;
import br.com.matheuscirillo.cqrs.example.domain.security.UserToken;
import br.com.matheuscirillo.cqrs.example.domain.services.UserAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountFacadeImpl implements UserAccountFacade {

    @Autowired
    private UserAccountService service;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Integer register(RegistrationRequest request) {
        return service.create(mapper.map(request, UserAccount.class)).getId();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            UserToken userToken = service.authenticate(request.getUsername(), request.getPassword());

            return new AuthenticationResponse(userToken.getValue(), userToken.getExpiresIn());
        } catch (UnauthorizedException e) {
            throw new ApiException(ErrorType.UNAUTHORIZED, e.getMessage());
        }
    }
}
