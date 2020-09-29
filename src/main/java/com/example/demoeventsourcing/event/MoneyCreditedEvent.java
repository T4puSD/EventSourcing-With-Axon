package com.example.demoeventsourcing.event;

public class MoneyCreditedEvent extends BaseEvent<String>{
    public final Double creditAmount;
    public final String currency;

    public MoneyCreditedEvent(String id, Double creditAmount, String currency) {
        super(id);
        this.creditAmount = creditAmount;
        this.currency = currency;
    }
}
