package com.kirill.vakhrushev.fitcenter.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserEntryEvent extends Event {

    private final LocalDateTime entryDateTime;

    public UserEntryEvent(LocalDateTime entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public LocalDateTime getEntryDateTime() {
        return entryDateTime;
    }

    @Override
    public String toString() {
        return "UserEntryEvent{" +
                "entryDateTime=" + entryDateTime +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserEntryEvent)) {
            return false;
        }
        UserEntryEvent that = (UserEntryEvent) o;
        return Objects.equals(entryDateTime, that.entryDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryDateTime);
    }
}
