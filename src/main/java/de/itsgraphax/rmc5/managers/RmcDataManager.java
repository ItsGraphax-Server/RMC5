package de.itsgraphax.rmc5.managers;

import de.itsgraphax.grphxLib.utils.DataManager;
import de.itsgraphax.rmc5.RmcPlugin;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import org.jetbrains.annotations.Nullable;

public class RmcDataManager extends DataManager {
    public RmcDataManager() {
        super("data.yml", RmcPlugin.instance());
    }

    public int getCrafts(TokenIdentifier id) {
        return data.getInt(String.format("crafts.%s", id.id()), 0);
    }
    public void setCrafts(TokenIdentifier id, int amount) {
        data.set(String.format("crafts.%s", id.id()), amount);
    }

    public @Nullable Long getNextTrader() {
        return data.getLong("nextTrader");
    }
    public void setNextTrader(long val) {
        data.set("nextTrader", val);
    }
}
