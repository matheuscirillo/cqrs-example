package br.com.matheuscirillo.cqrs.example.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.stereotype.Repository;

import br.com.matheuscirillo.cqrs.example.domain.entity.BankAccount;
import br.com.matheuscirillo.cqrs.example.domain.entity.Transaction;

@Repository
public class BankAccountRepository {

    @Autowired
    private ElasticsearchOperations elasticOperations;

    public void save(BankAccount bankAccount) {
	elasticOperations.save(bankAccount);
    }

    public Optional<BankAccount> getById(Integer bankAccountId) {
	return Optional.ofNullable(elasticOperations.get(String.valueOf(bankAccountId), BankAccount.class));
    }

    public Optional<List<Transaction>> getTransactionsByBankAccountId(Integer bankAccountId) {
	NativeSearchQuery query = new NativeSearchQuery(QueryBuilders.idsQuery().addIds(String.valueOf(bankAccountId)));
	SourceFilter filter = new FetchSourceFilter(new String[] { "transactions.*" }, null);
	query.addSourceFilter(filter);

	SearchHits<BankAccount> result = elasticOperations.search(query, BankAccount.class);
	if (result.getTotalHits() > 0) {
	    return Optional.of(result.stream().findFirst().get().getContent().getTransactions());
	} else {
	    return Optional.empty();
	}
    }
}
