package br.com.matheuscirillo.cqrs.example.infrastructure.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.matheuscirillo.cqrs.example.domain.event.account.BankAccountEvent;

@Component
@SuppressWarnings({ "rawtypes", "unchecked"})
public class BankAccountEventPublisher {

    private final String TOPIC_NAME = "bank-account-events";

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private ObjectMapper mapper;

    public void publish(List<BankAccountEvent> event) throws JsonProcessingException {
	kafkaTemplate.send(TOPIC_NAME, mapper.writeValueAsString(event));
    }

}
