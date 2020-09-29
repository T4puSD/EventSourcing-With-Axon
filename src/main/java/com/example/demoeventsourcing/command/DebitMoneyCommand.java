package com.example.demoeventsourcing.command;

public class DebitMoneyCommand extends BaseCommand<String>{
    public final Double debitAmount;
    public final String currency;

    public DebitMoneyCommand(String id, Double debitAmount, String currency) {
        super(id);
        this.debitAmount = debitAmount;
        this.currency = currency;
    }
}
