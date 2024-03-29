package br.com.matheuscirillo.cqrs.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "bank-accounts")
public class BankAccount {

    @Id
    private Integer id;
    private Double balance;
    private List<Transaction> transactions = new ArrayList<>();
    private Date accountCreatedAt;

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

}
