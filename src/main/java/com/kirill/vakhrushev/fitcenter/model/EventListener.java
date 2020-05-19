package com.kirill.vakhrushev.fitcenter.model;

public interface EventListener {
    void handleEvent(long accountId, Event event);
}
