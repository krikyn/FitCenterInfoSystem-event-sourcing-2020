package com.kirill.vakhrushev.fitcenter.model;

import java.time.LocalDate;

public class StatInfo {

    private final LocalDate date;
    private final long entries;

    private StatInfo(LocalDate date, long entries) {
        this.date = date;
        this.entries = entries;
    }

    public static StatInfo of(LocalDate date, long entries) {
        return new StatInfo(date, entries);
    }

    public LocalDate getDate() {
        return date;
    }

    public long getEntries() {
        return entries;
    }
}
