package br.com.matheuscirillo.cqrs.example;

import java.util.concurrent.ExecutionException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Bootstrapper {

    public static void main(String[] args) throws JsonProcessingException, InterruptedException, ExecutionException {
	SpringApplication.run(Bootstrapper.class, args);
    }

}
