package de.itsgraphax.rmc5.events;

import de.itsgraphax.rmc5.events.bounty.Bounty;
import de.itsgraphax.rmc5.events.none.None;

import java.util.function.Supplier;

public enum EventIdentifier {
    NONE(None::new),
    BOUNTY(Bounty::new);

    private final Supplier<Event> factory;

    EventIdentifier(Supplier<Event> factory) {
        this.factory = factory;
    }

    public Event create() {
        return factory.get();
    }
}
