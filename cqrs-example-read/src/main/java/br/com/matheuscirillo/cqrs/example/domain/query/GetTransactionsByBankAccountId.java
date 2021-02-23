package br.com.matheuscirillo.cqrs.example.domain.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTransactionsByBankAccountId {

    private Integer bankAccountId;

}
