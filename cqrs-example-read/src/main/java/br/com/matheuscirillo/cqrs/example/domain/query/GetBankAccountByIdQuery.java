package br.com.matheuscirillo.cqrs.example.domain.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetBankAccountByIdQuery {

    private Integer id;

}
