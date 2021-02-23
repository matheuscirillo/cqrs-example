package br.com.matheuscirillo.cqrs.example.domain.services.impl;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;

import br.com.matheuscirillo.cqrs.example.domain.entity.UserAccount;
import br.com.matheuscirillo.cqrs.example.domain.exception.UnauthorizedException;
import br.com.matheuscirillo.cqrs.example.domain.security.UserToken;
import br.com.matheuscirillo.cqrs.example.domain.services.UserAccountService;
import br.com.matheuscirillo.cqrs.example.infrastructure.TokenRepository;
import br.com.matheuscirillo.cqrs.example.infrastructure.UserAccountRepository;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository uap;
    
    @Autowired
    private TokenRepository tokenRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserAccount create(UserAccount userAccount) {
	userAccount.setPassword(encoder.encode(userAccount.getPassword()));
	return uap.save(userAccount);
    }

    @Override
    public UserToken authenticate(String username, String password) throws UnauthorizedException {
	UserAccount userAccount = uap.findByUsername(username);
	if (userAccount != null) {
	    if (encoder.matches(password, userAccount.getPassword())) {
		UserToken userToken = createJWTToken(userAccount);
		tokenRepo.store(userAccount.getId(), userToken.getValue());
		
		return userToken;
	    }
	}
	
	throw new UnauthorizedException("Usuário não autorizado");
    }

    private UserToken createJWTToken(UserAccount userAccount) {
	Date expiresAt = new Date(System.currentTimeMillis() + 864_000_000);
	String token = JWT.create().withSubject(userAccount.getId().toString())
			.withExpiresAt(expiresAt)
			.sign(HMAC512("EnfA3Rhdep".getBytes()));
	
	return new UserToken(token, expiresAt.getTime());
    }

}
