package com.example.demoeventsourcing.event;

public class AccountCreatedEvent extends BaseEvent<String>{
    public final Double accountBalance;
    public final String currency;

    public AccountCreatedEvent(String id, Double accountBalance, String currency) {
        super(id);
        this.accountBalance = accountBalance;
        this.currency = currency;
    }
}
