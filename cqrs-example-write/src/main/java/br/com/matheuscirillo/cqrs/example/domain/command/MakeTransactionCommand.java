package br.com.matheuscirillo.cqrs.example.domain.command;

import br.com.matheuscirillo.cqrs.example.domain.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakeTransactionCommand {
    
    private Integer accountId;
    private TransactionType type;
    private Double amount;
        
}
