package br.com.matheuscirillo.cqrs.example.api.exception;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import br.com.matheuscirillo.cqrs.example.api.exception.error.ErrorType;
import br.com.matheuscirillo.cqrs.example.domain.exception.BankAccountAlreadyExistsException;
import br.com.matheuscirillo.cqrs.example.domain.exception.BankAccountDoNotExistsException;
import br.com.matheuscirillo.cqrs.example.domain.exception.TransactionException;

@Aspect
@Component
public class ExceptionTranslator {

    
    @AfterThrowing(value = "within(br.com.matheuscirillo.cqrs.example.domain.command.handler.*)", throwing = "ex")
    public void translate(Exception ex) throws ApiException {
	if (ex instanceof TransactionException)
	    throw new ApiException(ErrorType.TransactionError, ex.getMessage());
	else if (ex instanceof BankAccountAlreadyExistsException)
	    throw new ApiException(ErrorType.AccountAlreadyExistsError, ex.getMessage());
	else if (ex instanceof BankAccountDoNotExistsException)
	    throw new ApiException(ErrorType.AccountNotFoundError, ex.getMessage());
    }
}
