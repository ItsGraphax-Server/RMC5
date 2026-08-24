package de.itsgraphax.rmc5.customItems.listeners;

import de.itsgraphax.rmc5.HasPlugin;
import io.papermc.paper.advancement.AdvancementDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementListener implements Listener, HasPlugin {
    @EventHandler
    void onAdvancement(PlayerAdvancementDoneEvent e) {
        AdvancementDisplay display = e.getAdvancement().getDisplay();
        if (display == null) return;

        if (display.frame() != AdvancementDisplay.Frame.CHALLENGE) return;

        e.getPlayer().give(plugin.citemManager()
                .get(plugin.namespaces().itemNoxiumIngot())
                .createItem()
        );
    }
}
