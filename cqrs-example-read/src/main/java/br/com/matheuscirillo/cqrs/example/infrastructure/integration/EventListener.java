package br.com.matheuscirillo.cqrs.example.infrastructure.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.matheuscirillo.cqrs.example.domain.event.Event;
import br.com.matheuscirillo.cqrs.example.domain.event.EventType;
import br.com.matheuscirillo.cqrs.example.domain.event.handler.EventHandler;

@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EventListener {

    @Autowired
    private EventHandlerRegistry registry;

    @Autowired
    private ObjectMapper mapper;

    /**
     * 
     * Listener genérico que escuta um ou mais tópicos do Kafka. O evento recebido
     * DEVE ser um JSON, caso contrário, uma exceção é lançada. Ao receber um
     * evento, o Listener transforma a String em um JsonNode, identifica o tipo de
     * evento através do atributo <code>_metadata.type</code> deste JsonNode e então consegue obter
     * o handler correto no EventHandlerRegistry
     * 
     * @param publishedEvent
     * @throws JsonMappingException
     * @throws JsonProcessingException
     * 
     */
    @KafkaListener(topics = { "bank-account-events" }, concurrency = "1")
    public void listen(String publishedEvent) throws JsonMappingException, JsonProcessingException {
	JsonNode eventAsJson = mapper.readTree(publishedEvent);
	if (eventAsJson.isArray()) {
	    for (JsonNode event : eventAsJson) {
		handleEvent(event);
	    }
	} else {
	    handleEvent(eventAsJson);
	}
    }

    private void handleEvent(JsonNode event) throws JsonProcessingException {
	EventType eventType = EventType.valueOf(event.get("_metadata").get("type").asText());
	EventHandler handler = registry.getHandler(eventType);

	handler.handle((Event) mapper.treeToValue(event, handler.getTypeHandled()));
    }
}
