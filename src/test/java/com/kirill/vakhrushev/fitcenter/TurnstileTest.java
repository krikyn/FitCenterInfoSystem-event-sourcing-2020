package com.kirill.vakhrushev.fitcenter;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.kirill.vakhrushev.fitcenter.core.Turnstile;
import com.kirill.vakhrushev.fitcenter.model.*;
import com.kirill.vakhrushev.fitcenter.storage.EventStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TurnstileTest {

    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2020, 10, 6, 13, 40);
    private static final LocalDate LOCAL_DATE = LOCAL_DATE_TIME.toLocalDate();

    @Mock
    private EventStorage eventStorageMock;
    private Clock fixedClock = Clock.fixed(Instant.parse("2020-10-06T10:40:00Z"), ZoneId.systemDefault());

    private Turnstile turnstile;

    @BeforeEach
    void setup() {
        this.turnstile = new Turnstile(eventStorageMock, fixedClock);
    }

    @Test
    void testEntry() {
        List<Event> list = new LinkedList<>();
        list.add(new AccountExtensionEvent(LOCAL_DATE, 7));
        Mockito.when(eventStorageMock.get(eq(1L)))
                .thenReturn(list);

        turnstile.entry(1L);
        Mockito.verify(eventStorageMock, times(1))
                .save(eq(1L), eq(new UserEntryEvent(LOCAL_DATE_TIME)));
    }

    @Test
    void testEscape() {
        turnstile.escape(1L);
        Mockito.verify(eventStorageMock, times(1))
                .save(eq(1L), eq(new UserEscapeEvent(LOCAL_DATE_TIME)));
    }

    @Test
    void testCantEntry() {
        List<Event> list = new LinkedList<>();
        list.add(new AccountExtensionEvent(LOCAL_DATE.minusDays(10), 1));
        Mockito.when(eventStorageMock.get(eq(1L)))
                .thenReturn(list);

        Assertions.assertThrows(
                RuntimeException.class,
                () -> turnstile.entry(1L)
        );
    }
}
