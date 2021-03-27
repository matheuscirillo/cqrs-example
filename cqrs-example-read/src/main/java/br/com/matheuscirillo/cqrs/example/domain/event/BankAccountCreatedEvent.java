package br.com.matheuscirillo.cqrs.example.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountCreatedEvent extends Event {

    private Integer accountId;
    private String accountType;
    private Date accountCreatedAt;

}
