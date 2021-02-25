package br.com.matheuscirillo.cqrs.example.domain.entity.aggregate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.matheuscirillo.cqrs.example.domain.entity.Transaction;
import br.com.matheuscirillo.cqrs.example.domain.entity.TransactionType;
import br.com.matheuscirillo.cqrs.example.domain.event.Event;
import br.com.matheuscirillo.cqrs.example.domain.event.account.BankAccountCreatedEvent;
import br.com.matheuscirillo.cqrs.example.domain.event.account.BankAccountEvent;
import br.com.matheuscirillo.cqrs.example.domain.event.account.TransactionEvent;
import br.com.matheuscirillo.cqrs.example.domain.exception.BankAccountAlreadyExistsException;
import br.com.matheuscirillo.cqrs.example.domain.exception.BankAccountDoNotExistsException;
import br.com.matheuscirillo.cqrs.example.domain.exception.TransactionException;

@SuppressWarnings("unused")
public class BankAccount {

    private Integer id;
    private String type;
    private Map<String, Transaction> transactions = new HashMap<>();
    private Date createdAt;
    private Double balance = 0.0;

    private List<BankAccountEvent> changes = new ArrayList<>();

    public BankAccount(List<BankAccountEvent> events) {
	for (Event event : events) {
	    if (event instanceof TransactionEvent)
		apply((TransactionEvent) event, false);
	    else if (event instanceof BankAccountCreatedEvent)
		apply((BankAccountCreatedEvent) event, false);
	}
    }

    /**
     * 
     * Efetua a criação de uma conta
     * 
     * @param id   Identificador da conta
     * @param type Tipo de conta (PF ou PJ)
     * 
     */
    public BankAccountCreatedEvent create(Integer id, String type) {
	if (this.createdAt != null)
	    throw new BankAccountAlreadyExistsException("A conta já existe");

	BankAccountCreatedEvent event = new BankAccountCreatedEvent(id, type, new Date());
	apply(event, true);

	return event;
    }

    /**
     * 
     * Efetua uma ação de depósito
     * 
     * @param amount Valor que está sendo depositado
     * 
     */
    public TransactionEvent deposit(Double amount) {
	if (this.createdAt == null)
	    throw new BankAccountDoNotExistsException("Não é possível efetuar um depósito nesta conta. Ela não existe");

	TransactionEvent event = new TransactionEvent(this.id, TransactionType.Deposit, amount);
	apply(event, true);
	
	return event;
    }

    /**
     * 
     * Efetua uma ação de saque.
     * 
     * @param amount Valor que esta sendo sacado
     * @throws TransactionException Exception se o saldo for insuficiente para
     *                              efetuar o saque
     * 
     */
    public TransactionEvent withdraw(Double amount) {
	if (this.createdAt == null)
	    throw new BankAccountDoNotExistsException("Não é possível efetuar um saque desta conta. Ela não existe");

	if (balance < amount)
	    throw new TransactionException("Saldo insuficiente");
	
	TransactionEvent event = new TransactionEvent(this.id, TransactionType.Withdraw, amount);
	apply(event, true);
	
	return event;
    }

    /**
     * 
     * Aplica ao agregado eventos do tipo <code>TransactionEvent</code>
     * 
     * @param event Evento a ser aplicado
     * @param isNew Flag que indica se é um evento novo ou não. Se
     *              <code>true</code>, coloca o evento na lista de alterações que
     *              devem ser propagadas para a stream de eventos
     * 
     */
    private void apply(TransactionEvent event, boolean isNew) {
	this.transactions.put(event.getTransactionId().toString(),
		new Transaction(event.getTransactionId(), event.getTransactionType(), event.getTransactionAmount()));

	if (event.getTransactionType() == TransactionType.Deposit) {
	    balance += event.getTransactionAmount();
	} else if (event.getTransactionType() == TransactionType.Withdraw) {
	    balance -= event.getTransactionAmount();
	}

	if (isNew)
	    this.changes.add(event);
    }

    /**
     * 
     * Aplica ao agregado eventos do tipo <code>BankAccountCreatedEvent</code>
     * 
     * @param event Evento a ser aplicado
     * @param isNew Flag que indica se é um evento novo ou não. Se
     *              <code>true</code>, coloca o evento na lista de alterações que
     *              devem ser propagadas para a stream de eventos
     * 
     */
    private void apply(BankAccountCreatedEvent event, boolean isNew) {
	this.id = event.getAccountId();
	this.type = event.getAccountType();
	this.createdAt = event.getAccountCreatedAt();

	if (isNew)
	    this.changes.add(event);
    }

    /**
     * 
     * @return A lista de alterações que devem ser propagadas na stream de eventos
     */
    public List<BankAccountEvent> getChanges() {
	return changes;
    }

}