package br.com.matheuscirillo.cqrs.example.domain.event.account.aggregate;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import br.com.matheuscirillo.cqrs.example.domain.event.account.BankAccountEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "bank-account-events")
public class BankAccountEventAggregate {
    
    @Id
    private Integer bankAccountId;
    private List<BankAccountEvent> events;
    
}
