package br.com.matheuscirillo.cqrs.example.api.exception;

import br.com.matheuscirillo.cqrs.example.api.exception.error.ApiError;
import br.com.matheuscirillo.cqrs.example.api.exception.error.ErrorType;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private ApiError error;

    public ApiException(ErrorType errorType, String message) {
        this.error = new ApiError(errorType, message);
    }
}
