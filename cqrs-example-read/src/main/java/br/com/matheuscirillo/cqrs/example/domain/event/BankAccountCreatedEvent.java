package br.com.matheuscirillo.cqrs.example.domain.event;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountCreatedEvent extends Event {

    private Integer accountId;
    private String accountType;
    private Date accountCreatedAt;
    
}
