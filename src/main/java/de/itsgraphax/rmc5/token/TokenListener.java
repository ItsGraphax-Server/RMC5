package de.itsgraphax.rmc5.token;

import de.itsgraphax.rmc5.HasPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class TokenListener implements HasPlugin, Listener {
    @EventHandler
    void onDeath(PlayerDeathEvent e) {
        for (int slot = 0; slot < 2; slot++) { // loop over slots
            plugin.pdcData().setEquippedBroken(e.getPlayer(), slot, true);
        }
    }
}
