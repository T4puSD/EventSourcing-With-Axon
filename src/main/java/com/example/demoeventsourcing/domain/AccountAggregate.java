package com.example.demoeventsourcing.domain;

import com.example.demoeventsourcing.command.CreateAccountCommand;
import com.example.demoeventsourcing.command.CreditMoneyCommand;
import com.example.demoeventsourcing.command.DebitMoneyCommand;
import com.example.demoeventsourcing.domain.enamuration.Status;
import com.example.demoeventsourcing.event.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String id;
    private Double accountBalance;
    private String currency;
    private String status;

    public AccountAggregate(){

    }

    @CommandHandler
    public AccountAggregate (CreateAccountCommand createAccountCommand){
        AggregateLifecycle.apply(new AccountCreatedEvent(createAccountCommand.id,
                createAccountCommand.accountBalance,
                createAccountCommand.currency));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent accountCreatedEvent){
        this.id = accountCreatedEvent.id;
        this.accountBalance = accountCreatedEvent.accountBalance;
        this.currency = accountCreatedEvent.currency;
        this.status = String.valueOf(Status.CREATED);

        AggregateLifecycle.apply(new AccountActivatedEvent(this.id,Status.ACTIVATED));
    }

    @EventSourcingHandler
    protected void on(AccountActivatedEvent accountActivatedEvent){
        this.status = String.valueOf(accountActivatedEvent.status);
    }

    @CommandHandler
    protected void on(CreditMoneyCommand creditMoneyCommand){
        AggregateLifecycle.apply(new MoneyCreditedEvent(creditMoneyCommand.id,creditMoneyCommand.creditAmount,creditMoneyCommand.currency));

    }

    @EventSourcingHandler
    protected void on(MoneyCreditedEvent moneyCreditedEvent){

        if (this.accountBalance < 0 & (this.accountBalance + moneyCreditedEvent.creditAmount) >= 0){
            AggregateLifecycle.apply(new AccountActivatedEvent(this.id, Status.ACTIVATED));
        }

        this.accountBalance += moneyCreditedEvent.creditAmount;
    }

    @CommandHandler
    protected void on(DebitMoneyCommand debitMoneyCommand){
        AggregateLifecycle.apply(new MoneyDebitedEvent(debitMoneyCommand.id,debitMoneyCommand.debitAmount,debitMoneyCommand.currency));
    }

    @EventSourcingHandler
    protected void on(MoneyDebitedEvent moneyDebitedEvent){
        if(this.accountBalance >=0 & (this.accountBalance - moneyDebitedEvent.debitAmount) <0){
            AggregateLifecycle.apply(new AccountHeldEvent(moneyDebitedEvent.id,Status.HOLD));
        }

        this.accountBalance -= moneyDebitedEvent.debitAmount;
    }

    @EventSourcingHandler
    protected void on(AccountHeldEvent accountHeldEvent){
        this.status = String.valueOf(accountHeldEvent.status);
    }
}
