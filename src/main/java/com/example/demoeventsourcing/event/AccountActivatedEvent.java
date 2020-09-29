package com.example.demoeventsourcing.event;

import com.example.demoeventsourcing.domain.enamuration.Status;

public class AccountActivatedEvent extends BaseEvent<String>{
    public final Status status;

    public AccountActivatedEvent(String id, Status status) {
        super(id);
        this.status = status;
    }
}
