package com.kirill.vakhrushev.fitcenter;

import com.kirill.vakhrushev.fitcenter.core.ManagerAdmin;
import com.kirill.vakhrushev.fitcenter.model.AccountCreatedEvent;
import com.kirill.vakhrushev.fitcenter.model.AccountExtensionEvent;
import com.kirill.vakhrushev.fitcenter.model.Event;
import com.kirill.vakhrushev.fitcenter.storage.EventStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ManagerAdminTest {

    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2020, 10, 6, 10, 40);
    private static final LocalDate LOCAL_DATE = LOCAL_DATE_TIME.toLocalDate();

    @Mock
    private EventStorage eventStorageMock;
    private Clock fixedClock = Clock.fixed(Instant.parse("2020-10-06T10:40:00Z"), ZoneId.systemDefault());

    private ManagerAdmin managerAdmin;

    @BeforeEach
    void setup() {
        this.managerAdmin = new ManagerAdmin(eventStorageMock, fixedClock);
    }

    @Test
    void testCreateAccount() {
        Mockito.when(eventStorageMock.empty(eq(1L)))
                .thenReturn(true);
        managerAdmin.createAccount(1L);

        Mockito.verify(eventStorageMock, times(1))
                .save(eq(1L), eq(new AccountCreatedEvent(LOCAL_DATE)));

        Mockito.verifyNoMoreInteractions(eventStorageMock);
    }

    @Test
    void testExtensionAccount() {
        Mockito.when(eventStorageMock.get(eq(1L)))
                .thenReturn(new LinkedList<Event>() {{
                    add(new AccountCreatedEvent(LOCAL_DATE));
                }});
        managerAdmin.extensionAccount(1L, LOCAL_DATE, 7);

        Mockito.verify(eventStorageMock, times(1))
                .save(eq(1L), eq(new AccountExtensionEvent(LOCAL_DATE, 7)));

        Mockito.verifyNoMoreInteractions(eventStorageMock);
    }

    @Test
    void testCanExtensionAccount() {
        Mockito.when(eventStorageMock.get(eq(1L)))
                .thenReturn(new LinkedList<Event>() {{
                    add(new AccountCreatedEvent(LOCAL_DATE));
                    add(new AccountExtensionEvent(LOCAL_DATE, 1));
                }});

        Assertions.assertThrows(
                RuntimeException.class,
                () -> managerAdmin.extensionAccount(1L, LOCAL_DATE, 7)
        );
    }
}
