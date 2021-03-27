package br.com.matheuscirillo.cqrs.example.domain.event;

import br.com.matheuscirillo.cqrs.example.domain.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEvent extends Event {

    private Integer bankAccountId;
    private UUID transactionId = UUID.randomUUID();
    private TransactionType transactionType;
    private Double transactionAmount;

}
