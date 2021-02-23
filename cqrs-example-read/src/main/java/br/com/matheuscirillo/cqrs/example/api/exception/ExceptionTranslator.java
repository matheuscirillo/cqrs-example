package br.com.matheuscirillo.cqrs.example.api.exception;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import br.com.matheuscirillo.cqrs.example.api.exception.error.ErrorType;
import br.com.matheuscirillo.cqrs.example.domain.exception.BankAccountNotFoundException;

@Aspect
@Component
public class ExceptionTranslator {

    
    @AfterThrowing(value = "within(br.com.matheuscirillo.cqrs.example.domain.query.handler.*)", throwing = "ex")
    public void translate(Exception ex) throws ApiException {
	if (ex instanceof BankAccountNotFoundException)
	    throw new ApiException(ErrorType.AccountNotFoundError, ex.getMessage());
    }
}
