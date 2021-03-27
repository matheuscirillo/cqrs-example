package br.com.matheuscirillo.cqrs.example.domain.event.handler;

import br.com.matheuscirillo.cqrs.example.domain.event.Event;
import br.com.matheuscirillo.cqrs.example.domain.event.EventType;

public interface EventHandler<T extends Event> {

    /**
     * Trata o evento
     *
     * @param event Evento que deve ser tratado
     */
    void handle(T event);

    /**
     * @return EventType que o handler deve tratar
     */
    EventType getEventType();

    /**
     * @return Tipo de <code>T</code> (T.class)
     */
    Class<T> getTypeHandled();
}
