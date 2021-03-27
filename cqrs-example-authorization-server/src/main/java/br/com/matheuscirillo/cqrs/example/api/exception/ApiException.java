package br.com.matheuscirillo.cqrs.example.api.exception;

import lombok.Data;

@Data
public class ApiException extends RuntimeException {

    private ApiError error;

    public ApiException(ErrorType errorType, String message) {
        this.error = new ApiError(errorType, message);
    }
}
