package de.itsgraphax.rmc5.events;

import de.itsgraphax.grphxLib.shorthands.OnEnable;

import java.util.HashMap;
import java.util.Map;

import static de.itsgraphax.rmc5.RmcPlugin.rmc;

public final class EventManager {
    private final Map<EventIdentifier, Event> events = new HashMap<>();

    public EventManager() {
        for (EventIdentifier id : EventIdentifier.values()) {
            Event event = id.create();
            events.put(id, event);
        }
        OnEnable.registerEvents(rmc, events.values());
    }


    public void reloadConfig() {
        for (Event event : events.values()) {
            event.reloadConfig();
        }
    }


    public Event getEvent(EventIdentifier id) {
        return events.get(id);
    }


    public void tick() {
        Event event = getEvent(rmc.data().getCurrentEvent());
        event.tick();
    }
}
