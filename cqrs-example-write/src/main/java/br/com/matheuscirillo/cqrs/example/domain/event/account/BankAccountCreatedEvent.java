package br.com.matheuscirillo.cqrs.example.domain.event.account;

import br.com.matheuscirillo.cqrs.example.domain.event.EventType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BankAccountCreatedEvent extends BankAccountEvent {

    private Integer accountId;
    private String accountType;
    private Date accountCreatedAt;

    // necessário para que o Spring Data consiga construir o objeto
    public BankAccountCreatedEvent() {
        super(null, EventType.BankAccountCreatedEvent);
    }

    public BankAccountCreatedEvent(Integer accountId, String accountType, Date accountCreatedAt) {
        super(accountId, EventType.BankAccountCreatedEvent);
        this.accountId = accountId;
        this.accountType = accountType;
        this.accountCreatedAt = accountCreatedAt;
    }

}
