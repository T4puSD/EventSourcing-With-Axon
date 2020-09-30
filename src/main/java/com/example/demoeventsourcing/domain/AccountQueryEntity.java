package com.example.demoeventsourcing.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountQueryEntity {
    @Id
    private String id;
    private Double accountBalance;
    private String currency;
    private String status;


    public AccountQueryEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "AccountQueryEntity{" +
                "id='" + id + '\'' +
                ", accountBalance=" + accountBalance +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
