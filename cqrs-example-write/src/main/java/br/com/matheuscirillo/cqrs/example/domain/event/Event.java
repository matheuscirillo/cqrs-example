package br.com.matheuscirillo.cqrs.example.domain.event;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public abstract class Event {

    protected EventMetadata _metadata;

    public Event(EventType eventType) {
        this._metadata = new EventMetadata(UUID.randomUUID(), new Date().getTime(), eventType);
    }

}
