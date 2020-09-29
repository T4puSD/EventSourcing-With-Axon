package com.example.demoeventsourcing.event;

import com.example.demoeventsourcing.domain.enamuration.Status;

public class AccountHeldEvent extends BaseEvent<String>{
    public final Status status;

    public AccountHeldEvent(String id, Status status) {
        super(id);
        this.status = status;
    }
}
