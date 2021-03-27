package br.com.matheuscirillo.cqrs.example.domain.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {

    private String value;
    private Long expiresIn;

}
