package br.com.matheuscirillo.cqrs.example.domain.event.account;

import br.com.matheuscirillo.cqrs.example.domain.event.Event;
import br.com.matheuscirillo.cqrs.example.domain.event.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BankAccountEvent extends Event {
    
    protected Integer bankAccountId;
    
    public BankAccountEvent(Integer bankAccountId, EventType eventType) {
	super(eventType);
	this.bankAccountId = bankAccountId;
    }
}
