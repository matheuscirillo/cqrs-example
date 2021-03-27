package br.com.matheuscirillo.cqrs.example.infrastructure.integration;

import br.com.matheuscirillo.cqrs.example.domain.event.Event;
import br.com.matheuscirillo.cqrs.example.domain.event.EventType;
import br.com.matheuscirillo.cqrs.example.domain.event.handler.EventHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventHandlerRegistry {

    private Map<EventType, EventHandler<? extends Event>> registry = new HashMap<>();

    public void addHandler(EventHandler<? extends Event> handler) {
        registry.put(handler.getEventType(), handler);
    }

    public List<EventHandler<? extends Event>> getHandlers() {
        return registry.values().stream().collect(Collectors.toList());
    }

    public EventHandler<? extends Event> getHandler(EventType eventType) {
        return registry.get(eventType);
    }

}
