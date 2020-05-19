package com.kirill.vakhrushev.fitcenter.core;

import java.time.LocalDate;
import java.util.List;

import com.kirill.vakhrushev.fitcenter.model.Event;
import com.kirill.vakhrushev.fitcenter.model.EventListener;
import com.kirill.vakhrushev.fitcenter.model.StatInfo;
import com.kirill.vakhrushev.fitcenter.model.UserEntryEvent;
import com.kirill.vakhrushev.fitcenter.storage.LocalStorage;
import org.springframework.stereotype.Component;

@Component
public class ReportService implements EventListener {

    private final LocalStorage localStorage;

    public ReportService(LocalStorage localStorage) {
        this.localStorage = localStorage;
    }

    @Override
    public void handleEvent(long accountId, Event event) {
        if (event instanceof UserEntryEvent) {
            localStorage.incDate(((UserEntryEvent) event).getEntryDateTime().toLocalDate());
        }
    }

    public List<StatInfo> getDailyStat(LocalDate from, LocalDate to) {
        return localStorage.get(from, to);
    }
}
