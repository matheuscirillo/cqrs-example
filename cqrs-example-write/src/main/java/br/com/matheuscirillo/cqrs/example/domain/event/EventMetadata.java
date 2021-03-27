package br.com.matheuscirillo.cqrs.example.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventMetadata {

    private UUID id;
    private Long timestamp;
    private EventType type;

}
