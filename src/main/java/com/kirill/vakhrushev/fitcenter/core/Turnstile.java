package com.kirill.vakhrushev.fitcenter.core;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Comparator;

import com.kirill.vakhrushev.fitcenter.model.AccountExtensionEvent;
import com.kirill.vakhrushev.fitcenter.model.Events;
import com.kirill.vakhrushev.fitcenter.model.UserEntryEvent;
import com.kirill.vakhrushev.fitcenter.model.UserEscapeEvent;
import com.kirill.vakhrushev.fitcenter.storage.EventStorage;
import org.springframework.stereotype.Component;

@Component
public class Turnstile {

    private final EventStorage eventStorage;
    private final Clock clock;

    public Turnstile(EventStorage eventStorage, Clock clock) {
        this.eventStorage = eventStorage;
        this.clock = clock;
    }

    public void entry(long id) {
        final LocalDateTime now = LocalDateTime.now(clock);
        if (!canEntry(id, now)) {
            throw new RuntimeException("Account can't entry");
        }

        eventStorage.save(id, new UserEntryEvent(now));
    }

    public void escape(long id) {
        eventStorage.save(id, new UserEscapeEvent(LocalDateTime.now(clock)));
    }

    private boolean canEntry(long id, LocalDateTime now) {
        final LocalDateTime expireDateTime = eventStorage.get(id)
                .stream()
                .filter(e -> Events.is(e, AccountExtensionEvent.class))
                .map(e -> Events.get(e, AccountExtensionEvent.class))
                .max(Comparator.comparing(AccountExtensionEvent::getExtensionDateStart))
                .map(e -> e.getExtensionDateStart().plusDays(e.getDays()))
                .map(date -> date.plusDays(1).atStartOfDay())
                .orElse(LocalDateTime.MIN);

        return now.isBefore(expireDateTime);
    }
}
