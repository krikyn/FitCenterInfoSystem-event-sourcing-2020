package com.kirill.vakhrushev.fitcenter.model;

import java.time.LocalDate;
import java.util.Objects;

public class AccountCreatedEvent extends Event {

    private final LocalDate creationDate;

    public AccountCreatedEvent(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountCreatedEvent)) {
            return false;
        }
        AccountCreatedEvent that = (AccountCreatedEvent) o;
        return Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creationDate);
    }
}
