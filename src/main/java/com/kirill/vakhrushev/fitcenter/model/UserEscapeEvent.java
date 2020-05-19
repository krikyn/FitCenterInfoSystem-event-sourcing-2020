package com.kirill.vakhrushev.fitcenter.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserEscapeEvent extends Event {

    private final LocalDateTime escapeDateTime;

    public UserEscapeEvent(LocalDateTime escapeDateTime) {
        this.escapeDateTime = escapeDateTime;
    }

    public LocalDateTime getEscapeDateTime() {
        return escapeDateTime;
    }

    @Override
    public String toString() {
        return "UserEscapeEvent{" +
                "escapeDateTime=" + escapeDateTime +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserEscapeEvent)) {
            return false;
        }
        UserEscapeEvent that = (UserEscapeEvent) o;
        return Objects.equals(escapeDateTime, that.escapeDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(escapeDateTime);
    }
}
