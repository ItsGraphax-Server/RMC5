package de.itsgraphax.rmc5.token;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class TokenEvent extends PlayerEvent implements Cancellable {
    private boolean canelled = false;

    public TokenEvent(@NotNull Player player) {
        super(player);
    }


    @Override
    public boolean isCancelled() {
        return canelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.canelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }
}
