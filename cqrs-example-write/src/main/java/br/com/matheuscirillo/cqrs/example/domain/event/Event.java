package br.com.matheuscirillo.cqrs.example.domain.event;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public abstract class Event {

    protected EventMetadata _metadata;

    public Event(EventType eventType) {
	this._metadata = new EventMetadata(UUID.randomUUID(), new Date().getTime(), eventType);
    }
    
}
