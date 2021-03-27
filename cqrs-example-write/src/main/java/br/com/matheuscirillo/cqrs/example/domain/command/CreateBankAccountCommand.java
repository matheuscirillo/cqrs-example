package br.com.matheuscirillo.cqrs.example.domain.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBankAccountCommand {

    private Integer id;
    private String type;

}
