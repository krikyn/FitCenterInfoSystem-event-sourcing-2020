package com.kirill.vakhrushev.fitcenter.model;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public final class Events {

    private Events() {
        throw new UnsupportedOperationException();
    }

    public static <E extends Event> E get(Event e, Class<E> eventType) {
        if (is(e, eventType)) {
            return (E) e;
        }
        return null;
    }

    public static <E extends Event> boolean is(Event e, Class<E> eventType) {
        return eventType.isInstance(e);
    }
}
