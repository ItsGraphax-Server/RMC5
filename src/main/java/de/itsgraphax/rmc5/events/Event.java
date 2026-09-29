package de.itsgraphax.rmc5.events;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;

import static de.itsgraphax.rmc5.RmcPlugin.rmc;

public abstract class Event implements Listener {
    protected final EventIdentifier id;
    protected static ConfigurationSection config;

    protected Event(EventIdentifier id) {
        this.id = id;
        reloadConfig();
    }

    protected void reloadConfig() {
        config = rmc.getConfig().getConfigurationSection(String.format("events.%s", id.name()));
    }


    public boolean isEventRunning() {
        return rmc.data.getCurrentEvent() == id;
    }


    public void tick() {}

    public void onStart() {}

    public void onEnd() {}
}
