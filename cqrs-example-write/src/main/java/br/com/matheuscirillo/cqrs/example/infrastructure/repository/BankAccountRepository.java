package br.com.matheuscirillo.cqrs.example.infrastructure.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Repository;

import br.com.matheuscirillo.cqrs.example.domain.entity.aggregate.BankAccount;
import br.com.matheuscirillo.cqrs.example.domain.event.account.BankAccountEvent;
import br.com.matheuscirillo.cqrs.example.domain.event.account.aggregate.BankAccountEventAggregate;

@Repository
public class BankAccountRepository {

    @Autowired
    private ElasticsearchOperations elasticOperations;

    /**
     * 
     * Captura os eventos da stream e devolve um objeto BankAccount criado a partir
     * dos eventos. Caso não exista uma stream para a BankAccount de id igual ao
     * parâmetro <code>id</code>, também devolve uma BankAccount nova.
     * 
     * @param id Identificador da BankAccount
     * @return Optional contendo, caso exista, o agregado BankAccount
     * 
     */
    public BankAccount getById(Integer id) {
	Optional<BankAccountEventAggregate> agg = Optional
		.ofNullable(elasticOperations.get(String.valueOf(id), BankAccountEventAggregate.class));

	return agg.isPresent() ? new BankAccount(agg.get().getEvents()) : new BankAccount(Collections.emptyList());
    }

    /**
     * 
     * Método que de fato propaga os eventos para a stream (neste caso, a stream é
     * um documento dentro de um índice no Elasticsearch). Se a stream ainda não
     * existe (no caso de uma conta nova, por exemplo), ela é criada.
     * 
     * @param id Identificador da BankAccount
     * 
     */
    public void appendToStream(Integer id, List<BankAccountEvent> events) {
	BankAccountEventAggregate agg = elasticOperations.get(String.valueOf(id), BankAccountEventAggregate.class);
	if (agg == null) {
	    agg = new BankAccountEventAggregate(id, events);
	} else {
	    agg.getEvents().addAll(events);
	}

	elasticOperations.save(agg);
    }
}
