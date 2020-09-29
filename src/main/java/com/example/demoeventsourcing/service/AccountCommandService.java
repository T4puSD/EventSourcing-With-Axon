package com.example.demoeventsourcing.service;

import com.example.demoeventsourcing.dto.AccountCreateDTO;
import com.example.demoeventsourcing.dto.MoneyCreditDTO;
import com.example.demoeventsourcing.dto.MoneyDebitDTO;

import java.util.concurrent.CompletableFuture;

public interface AccountCommandService {

    public CompletableFuture<String> createAccount(AccountCreateDTO accountCreateDTO);
    public CompletableFuture<String> creditMoneyToAccount(String accountNumber, MoneyCreditDTO moneyCreditDTO);
    public CompletableFuture<String> debitMoneyFromAccount(String accountNumber, MoneyDebitDTO moneyDebitDTO);

}
