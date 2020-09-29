package com.example.demoeventsourcing.event;

public class MoneyDebitedEvent extends BaseEvent<String>{
    public final Double debitAmount;
    public final String currency;

    public MoneyDebitedEvent(String id, Double debitAmount, String currency) {
        super(id);
        this.debitAmount = debitAmount;
        this.currency = currency;
    }
}
