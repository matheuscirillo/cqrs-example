package br.com.matheuscirillo.cqrs.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Bootstrapper {

    public static void main(String[] args) throws JsonProcessingException, InterruptedException, ExecutionException {
        SpringApplication.run(Bootstrapper.class, args);
    }

}
