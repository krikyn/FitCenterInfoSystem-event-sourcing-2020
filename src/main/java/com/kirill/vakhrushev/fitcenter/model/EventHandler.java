package com.kirill.vakhrushev.fitcenter.model;

import java.util.ArrayList;
import java.util.List;

public final class EventHandler {

    private final List<EventListener> listeners;

    public EventHandler() {
        this.listeners = new ArrayList<>();
    }

    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    public void handle(long id, Event e) {
        listeners.forEach(l -> l.handleEvent(id, e));
    }
}
