package br.com.matheuscirillo.cqrs.example.api.controller;

import br.com.matheuscirillo.cqrs.example.domain.command.CreateBankAccountCommand;
import br.com.matheuscirillo.cqrs.example.domain.command.MakeTransactionCommand;
import br.com.matheuscirillo.cqrs.example.domain.command.handler.BankAccountCommandHandler;
import br.com.matheuscirillo.cqrs.example.domain.event.account.BankAccountCreatedEvent;
import br.com.matheuscirillo.cqrs.example.domain.event.account.TransactionEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/bank-accounts")
public class BankAccountController {

    @Autowired
    private BankAccountCommandHandler handler;

    @PostMapping
    public ResponseEntity<Object> handleCreateAccount(@RequestBody CreateBankAccountCommand request)
            throws JsonProcessingException {
        request.setId(
                Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));

        BankAccountCreatedEvent event = handler.handle(request);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(event.getAccountId()).toUri()).build();
    }

    @PostMapping("/{id}/transactions")
    public ResponseEntity<Object> handleMakeTransaction(@PathVariable("id") Integer accountId,
                                                        @RequestBody MakeTransactionCommand request) throws JsonProcessingException {
        request.setAccountId(accountId);

        TransactionEvent event = handler.handle(request);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(event.getTransactionId()).toUri()).build();
    }

}
