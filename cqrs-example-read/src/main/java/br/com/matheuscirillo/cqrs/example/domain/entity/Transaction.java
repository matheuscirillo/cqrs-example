package br.com.matheuscirillo.cqrs.example.domain.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    
    private UUID id;
    private TransactionType type;
    private Double amount;
    
}
