package br.com.matheuscirillo.cqrs.example.domain.event.account;

import br.com.matheuscirillo.cqrs.example.domain.entity.TransactionType;
import br.com.matheuscirillo.cqrs.example.domain.event.EventType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TransactionEvent extends BankAccountEvent {

    private UUID transactionId = UUID.randomUUID();
    private TransactionType transactionType;
    private Double transactionAmount;

    // necessário para que o Spring Data consiga construir o objeto
    public TransactionEvent() {
        super(null, EventType.TransactionEvent);
    }

    public TransactionEvent(Integer bankAccountId, TransactionType type, Double amount) {
        super(bankAccountId, EventType.TransactionEvent);
        this.transactionType = type;
        this.transactionAmount = amount;
    }

    public TransactionEvent(Integer bankAccountId, UUID transactionId, TransactionType type, Double amount) {
        super(bankAccountId, EventType.TransactionEvent);
        this.transactionId = transactionId != null ? transactionId : UUID.randomUUID();
        this.transactionType = type;
        this.transactionAmount = amount;
    }

}
