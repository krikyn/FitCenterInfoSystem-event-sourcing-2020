package com.kirill.vakhrushev.fitcenter.core;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Comparator;

import com.kirill.vakhrushev.fitcenter.model.AccountCreatedEvent;
import com.kirill.vakhrushev.fitcenter.model.AccountExtensionEvent;
import com.kirill.vakhrushev.fitcenter.model.Events;
import com.kirill.vakhrushev.fitcenter.storage.EventStorage;
import org.springframework.stereotype.Component;

@Component
public class ManagerAdmin {

    private final EventStorage eventStorage;
    private final Clock clock;

    public ManagerAdmin(EventStorage eventStorage, Clock clock) {
        this.eventStorage = eventStorage;
        this.clock = clock;
    }

    public void createAccount(long id) {
        if (!eventStorage.empty(id)) {
            throw new RuntimeException("Account id must be unique");
        }

        final AccountCreatedEvent event = new AccountCreatedEvent(LocalDate.now(clock));
        eventStorage.save(id, event);
    }

    public void extensionAccount(long id, LocalDate extensionDate, int days) {
        final boolean isCreated = eventStorage.get(id)
                .stream()
                .findFirst()
                .map(e -> Events.is(e, AccountCreatedEvent.class))
                .orElse(false);

        if (!isCreated) {
            throw new RuntimeException("Account must be created");
        }

        validateExtension(id, extensionDate);

        final AccountExtensionEvent event = new AccountExtensionEvent(extensionDate, days);
        eventStorage.save(id, event);
    }

    private void validateExtension(long id, LocalDate extensionDate) {
        final LocalDate expireDate = eventStorage.get(id)
                .stream()
                .filter(e -> Events.is(e, AccountExtensionEvent.class))
                .map(e -> Events.get(e, AccountExtensionEvent.class))
                .max(Comparator.comparing(AccountExtensionEvent::getExtensionDateStart))
                .map(e -> e.getExtensionDateStart().plusDays(e.getDays()))
                .orElse(LocalDate.MIN);

        if (expireDate.isAfter(extensionDate)) {
            throw new RuntimeException("Account won't be expire");
        }
    }
}
