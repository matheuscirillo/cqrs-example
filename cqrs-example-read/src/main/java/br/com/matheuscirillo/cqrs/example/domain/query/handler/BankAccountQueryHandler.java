package br.com.matheuscirillo.cqrs.example.domain.query.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.matheuscirillo.cqrs.example.domain.entity.BankAccount;
import br.com.matheuscirillo.cqrs.example.domain.entity.Transaction;
import br.com.matheuscirillo.cqrs.example.domain.exception.BankAccountNotFoundException;
import br.com.matheuscirillo.cqrs.example.domain.query.GetBankAccountByIdQuery;
import br.com.matheuscirillo.cqrs.example.domain.query.GetTransactionsByBankAccountId;
import br.com.matheuscirillo.cqrs.example.infrastructure.repository.BankAccountRepository;

@Component
public class BankAccountQueryHandler {

    @Autowired
    private BankAccountRepository repository;

    public BankAccount handle(GetBankAccountByIdQuery query) {
	return repository.getById(query.getId())
		.orElseThrow(() -> new BankAccountNotFoundException("Conta não encontrada"));
    }

    public List<Transaction> handle(GetTransactionsByBankAccountId query) {
	return repository.getTransactionsByBankAccountId(query.getBankAccountId())
		.orElseThrow(() -> new BankAccountNotFoundException("Conta não encontrada"));
    }

}
