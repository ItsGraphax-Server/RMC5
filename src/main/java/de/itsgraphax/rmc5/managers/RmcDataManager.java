package de.itsgraphax.rmc5.managers;

import de.itsgraphax.grphxLib.utils.DataManager;
import de.itsgraphax.rmc5.RmcPlugin;
import de.itsgraphax.rmc5.events.EventIdentifier;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import org.jetbrains.annotations.Nullable;

import static de.itsgraphax.rmc5.RmcPlugin.rmc;

public class RmcDataManager extends DataManager {
    public RmcDataManager() {
        super("data.yml", RmcPlugin.instance());
    }

    public int getCrafts(TokenIdentifier id) {
        return data.getInt(String.format("crafts.%s", id.id()), 0);
    }
    public void setCrafts(TokenIdentifier id, int amount) {
        data.set(String.format("crafts.%s", id.id()), amount);
        save();
    }

    public @Nullable Long getNextTrader() {
        return data.getLong("nextTrader");
    }
    public void setNextTrader(long val) {
        data.set("nextTrader", val);
        save();
    }

    public EventIdentifier getCurrentEvent() {
        return EventIdentifier.valueOf(data.getString("currentEvent", "NONE"));
    }
    public void setCurrentEvent(EventIdentifier id) {
        EventIdentifier lastEvent = getCurrentEvent();
        data.set("currentEvent", id.toString());
        rmc.eventManager.getEvent(lastEvent).onEnd();
        rmc.eventManager.getEvent(id).onStart();
    }

    public Integer getBountyCycle() {
        return data.getInt("events.bounty.cycle", -1);
    }
    public void setBountyCycle(int val) {
        data.set("events.bounty.cycle", val);
        save();
    }
}
