package br.com.matheuscirillo.cqrs.example.domain.command.handler;

import br.com.matheuscirillo.cqrs.example.domain.command.CreateBankAccountCommand;
import br.com.matheuscirillo.cqrs.example.domain.command.MakeTransactionCommand;
import br.com.matheuscirillo.cqrs.example.domain.entity.TransactionType;
import br.com.matheuscirillo.cqrs.example.domain.entity.aggregate.BankAccount;
import br.com.matheuscirillo.cqrs.example.domain.event.account.BankAccountCreatedEvent;
import br.com.matheuscirillo.cqrs.example.domain.event.account.TransactionEvent;
import br.com.matheuscirillo.cqrs.example.infrastructure.integration.BankAccountEventPublisher;
import br.com.matheuscirillo.cqrs.example.infrastructure.repository.BankAccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankAccountCommandHandler {

    @Autowired
    private BankAccountRepository repository;

    @Autowired
    private BankAccountEventPublisher eventDispatcher;

    public BankAccountCreatedEvent handle(CreateBankAccountCommand cmd) throws JsonProcessingException {
        BankAccount bankAccount = repository.getById(cmd.getId());
        BankAccountCreatedEvent event = bankAccount.create(cmd.getId(), cmd.getType());

        repository.appendToStream(cmd.getId(), bankAccount.getChanges());
        eventDispatcher.publish(bankAccount.getChanges());

        return event;
    }

    public TransactionEvent handle(MakeTransactionCommand cmd) throws JsonProcessingException {
        BankAccount bankAccount = repository.getById(cmd.getAccountId());
        TransactionEvent event = null;
        if (cmd.getType() == TransactionType.Deposit)
            event = bankAccount.deposit(cmd.getAmount());
        else if (cmd.getType() == TransactionType.Withdraw)
            event = bankAccount.withdraw(cmd.getAmount());

        repository.appendToStream(cmd.getAccountId(), bankAccount.getChanges());
        eventDispatcher.publish(bankAccount.getChanges());

        return event;
    }

}
