package br.com.matheuscirillo.cqrs.example.domain.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.matheuscirillo.cqrs.example.domain.command.CreateBankAccountCommand;
import br.com.matheuscirillo.cqrs.example.domain.command.MakeTransactionCommand;
import br.com.matheuscirillo.cqrs.example.domain.entity.TransactionType;
import br.com.matheuscirillo.cqrs.example.domain.entity.aggregate.BankAccount;
import br.com.matheuscirillo.cqrs.example.infrastructure.integration.BankAccountEventPublisher;
import br.com.matheuscirillo.cqrs.example.infrastructure.repository.BankAccountRepository;

@Component
public class BankAccountCommandHandler {

    @Autowired
    private BankAccountRepository repository;
    
    @Autowired
    private BankAccountEventPublisher eventDispatcher;

    public void handle(CreateBankAccountCommand cmd) throws JsonProcessingException {
	BankAccount bankAccount = repository.getById(cmd.getId());
	bankAccount.create(cmd.getId(), cmd.getType());
	
	repository.appendToStream(cmd.getId(), bankAccount.getChanges());
	eventDispatcher.publish(bankAccount.getChanges());
    }

    public void handle(MakeTransactionCommand cmd) throws JsonProcessingException {
	BankAccount bankAccount = repository.getById(cmd.getAccountId());
	if (cmd.getType() == TransactionType.Deposit)
	    bankAccount.deposit(cmd.getAmount());
	else if (cmd.getType() == TransactionType.Withdraw)
	    bankAccount.withdraw(cmd.getAmount());

	repository.appendToStream(cmd.getAccountId(), bankAccount.getChanges());
	eventDispatcher.publish(bankAccount.getChanges());
    }

}
