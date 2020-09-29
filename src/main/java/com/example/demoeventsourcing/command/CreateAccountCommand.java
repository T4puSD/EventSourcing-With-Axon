package com.example.demoeventsourcing.command;

public class CreateAccountCommand extends BaseCommand<String>{

    public final Double accountBalance;
    public final String currency;

    public CreateAccountCommand(String id, Double accountBalance, String currency) {
        super(id);
        this.accountBalance = accountBalance;
        this.currency = currency;
    }
}
