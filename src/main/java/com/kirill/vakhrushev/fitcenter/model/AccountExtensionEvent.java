package com.kirill.vakhrushev.fitcenter.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AccountExtensionEvent extends Event {

    @Nonnull
    private final LocalDate extensionDateStart;
    private final int days;

    public AccountExtensionEvent(LocalDate extensionDateStart, int days) {
        this.extensionDateStart = extensionDateStart;
        this.days = days;
    }

    @Nonnull
    public LocalDate getExtensionDateStart() {
        return extensionDateStart;
    }

    public int getDays() {
        return days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountExtensionEvent)) {
            return false;
        }
        AccountExtensionEvent that = (AccountExtensionEvent) o;
        return days == that.days &&
                extensionDateStart.equals(that.extensionDateStart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extensionDateStart, days);
    }
}
