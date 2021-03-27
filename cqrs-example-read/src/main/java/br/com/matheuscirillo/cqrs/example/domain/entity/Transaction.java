package br.com.matheuscirillo.cqrs.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private UUID id;
    private TransactionType type;
    private Double amount;

}
