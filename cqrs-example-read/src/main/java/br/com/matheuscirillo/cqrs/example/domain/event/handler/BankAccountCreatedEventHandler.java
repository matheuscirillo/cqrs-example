package br.com.matheuscirillo.cqrs.example.domain.event.handler;

import br.com.matheuscirillo.cqrs.example.domain.entity.BankAccount;
import br.com.matheuscirillo.cqrs.example.domain.event.BankAccountCreatedEvent;
import br.com.matheuscirillo.cqrs.example.domain.event.EventType;
import br.com.matheuscirillo.cqrs.example.infrastructure.repository.BankAccountRepository;

public class BankAccountCreatedEventHandler implements EventHandler<BankAccountCreatedEvent> {

    private BankAccountRepository repository;

    public BankAccountCreatedEventHandler(BankAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(BankAccountCreatedEvent event) {
        repository.save(new BankAccount(event.getAccountId(), 0.0, null, event.getAccountCreatedAt()));
    }

    @Override
    public Class<BankAccountCreatedEvent> getTypeHandled() {
        return BankAccountCreatedEvent.class;
    }

    @Override
    public EventType getEventType() {
        return EventType.BankAccountCreatedEvent;
    }

}
