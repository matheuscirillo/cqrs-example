package br.com.matheuscirillo.cqrs.example.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.matheuscirillo.cqrs.example.domain.query.GetBankAccountByIdQuery;
import br.com.matheuscirillo.cqrs.example.domain.query.GetTransactionsByBankAccountId;
import br.com.matheuscirillo.cqrs.example.domain.query.handler.BankAccountQueryHandler;

@RestController
@RequestMapping(path = "/bank-accounts")
public class BankAccountController {

    @Autowired
    private BankAccountQueryHandler handler;

    @GetMapping("/{id}")
    public ResponseEntity<Object> handleGetBankAccount(@PathVariable("id") Integer id) {
	return ResponseEntity.ok(handler.handle(new GetBankAccountByIdQuery(id)));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<Object> handleGetTransactionsByBankAccountId(@PathVariable("id") Integer id) {
	return ResponseEntity.ok(handler.handle(new GetTransactionsByBankAccountId(id)));
    }

}
