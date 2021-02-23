package br.com.matheuscirillo.cqrs.example.domain.event.handler;

import java.util.Optional;

import br.com.matheuscirillo.cqrs.example.domain.entity.BankAccount;
import br.com.matheuscirillo.cqrs.example.domain.entity.Transaction;
import br.com.matheuscirillo.cqrs.example.domain.entity.TransactionType;
import br.com.matheuscirillo.cqrs.example.domain.event.EventType;
import br.com.matheuscirillo.cqrs.example.domain.event.TransactionEvent;
import br.com.matheuscirillo.cqrs.example.infrastructure.repository.BankAccountRepository;

public class TransactionEventHandler implements EventHandler<TransactionEvent> {

    private BankAccountRepository repository;

    public TransactionEventHandler(BankAccountRepository repository) {
	this.repository = repository;
    }

    @Override
    public void handle(TransactionEvent event) {
	Optional<BankAccount> optionalBankAccount = repository.getById(event.getBankAccountId());
	if (optionalBankAccount.isPresent()) {
	    BankAccount bankAccount = optionalBankAccount.get();
	    bankAccount.addTransaction(new Transaction(event.getTransactionId(), event.getTransactionType(),
		    event.getTransactionAmount()));
	    if (event.getTransactionType() == TransactionType.Deposit) {
		bankAccount.setBalance(bankAccount.getBalance() + event.getTransactionAmount());
	    } else if (event.getTransactionType() == TransactionType.Withdraw) {
		bankAccount.setBalance(bankAccount.getBalance() - event.getTransactionAmount());
	    }

	    repository.save(bankAccount);
	}
    }

    @Override
    public Class<TransactionEvent> getTypeHandled() {
	return TransactionEvent.class;
    }

    @Override
    public EventType getEventType() {
	return EventType.TransactionEvent;
    }

}
