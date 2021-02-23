package br.com.matheuscirillo.cqrs.example.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Event {

    protected EventMetadata _metadata;

}
