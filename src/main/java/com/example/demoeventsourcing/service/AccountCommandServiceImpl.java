package com.example.demoeventsourcing.service;

import com.example.demoeventsourcing.command.CreateAccountCommand;
import com.example.demoeventsourcing.command.CreditMoneyCommand;
import com.example.demoeventsourcing.command.DebitMoneyCommand;
import com.example.demoeventsourcing.dto.AccountCreateDTO;
import com.example.demoeventsourcing.dto.MoneyCreditDTO;
import com.example.demoeventsourcing.dto.MoneyDebitDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountCommandServiceImpl implements AccountCommandService{
    final CommandGateway commandGateway;

    public AccountCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<String> createAccount(AccountCreateDTO accountCreateDTO) {
        return commandGateway.send(
                new CreateAccountCommand(UUID.randomUUID().toString(),
                        accountCreateDTO.getStartingBalance(),
                        accountCreateDTO.getCurrency())
        );
    }

    @Override
    public CompletableFuture<String> creditMoneyToAccount(String accountNumber, MoneyCreditDTO moneyCreditDTO) {
        return commandGateway.send(
                new CreditMoneyCommand(accountNumber,
                        moneyCreditDTO.getCreditAmount(),
                        moneyCreditDTO.getCurrency())
        );
    }

    @Override
    public CompletableFuture<String> debitMoneyFromAccount(String accountNumber, MoneyDebitDTO moneyDebitDTO) {
        return commandGateway.send(
                new DebitMoneyCommand(accountNumber,
                        moneyDebitDTO.getDebitAmount(),
                        moneyDebitDTO.getCurrency())
        );
    }
}
