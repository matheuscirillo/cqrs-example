package br.com.matheuscirillo.cqrs.example.domain.event;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventMetadata {
    
    private UUID id;
    private Long timestamp;
    private EventType type;
    
}
