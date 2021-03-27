package br.com.matheuscirillo.cqrs.example.api.controller;

import br.com.matheuscirillo.cqrs.example.api.dto.AuthenticationRequest;
import br.com.matheuscirillo.cqrs.example.api.dto.RegistrationRequest;
import br.com.matheuscirillo.cqrs.example.api.facade.UserAccountFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountController {

    @Autowired
    private UserAccountFacade userAccountFacade;

    @PostMapping("/registration")
    public ResponseEntity<Object> handleRegister(@RequestBody RegistrationRequest request) {
        userAccountFacade.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/authentication")
    public ResponseEntity<Object> handleAuthenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userAccountFacade.authenticate(request));
    }

}
