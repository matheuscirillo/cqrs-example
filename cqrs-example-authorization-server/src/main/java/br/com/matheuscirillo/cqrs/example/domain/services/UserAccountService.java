package br.com.matheuscirillo.cqrs.example.domain.services;

import br.com.matheuscirillo.cqrs.example.domain.entity.UserAccount;
import br.com.matheuscirillo.cqrs.example.domain.exception.UnauthorizedException;
import br.com.matheuscirillo.cqrs.example.domain.security.UserToken;

public interface UserAccountService {

    UserAccount create(UserAccount userAccount);

    UserToken authenticate(String username, String password) throws UnauthorizedException;

}
