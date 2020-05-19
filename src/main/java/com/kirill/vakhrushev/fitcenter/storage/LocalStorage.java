package com.kirill.vakhrushev.fitcenter.storage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.kirill.vakhrushev.fitcenter.model.StatInfo;
import org.springframework.stereotype.Component;

@Component
public class LocalStorage {

    private Map<LocalDate, Long> storage;

    public LocalStorage() {
        this.storage = new ConcurrentHashMap<>();
    }

    public void incDate(LocalDate date) {
        final Long value = storage.getOrDefault(date, 0L);
        storage.put(date, value + 1);
    }

    public List<StatInfo> get(LocalDate from , LocalDate to) {
        return Stream.iterate(from, d -> d.plusDays(1))
                .limit(ChronoUnit.DAYS.between(from, to))
                .map(d -> StatInfo.of(d, storage.get(d)))
                .collect(Collectors.toList());
    }
}
