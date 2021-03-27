package br.com.matheuscirillo.cqrs.example.infrastructure.configuration;

import br.com.matheuscirillo.cqrs.example.domain.event.handler.BankAccountCreatedEventHandler;
import br.com.matheuscirillo.cqrs.example.domain.event.handler.TransactionEventHandler;
import br.com.matheuscirillo.cqrs.example.infrastructure.integration.EventHandlerRegistry;
import br.com.matheuscirillo.cqrs.example.infrastructure.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventHandlerRegistryConfigurer {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Bean
    public EventHandlerRegistry registerEventHandlers() {
        EventHandlerRegistry registry = new EventHandlerRegistry();
        registry.addHandler(new BankAccountCreatedEventHandler(bankAccountRepository));
        registry.addHandler(new TransactionEventHandler(bankAccountRepository));

        return registry;
    }

}
