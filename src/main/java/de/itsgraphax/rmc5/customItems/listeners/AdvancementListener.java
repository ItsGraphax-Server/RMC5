package de.itsgraphax.rmc5.customItems.listeners;

import io.papermc.paper.advancement.AdvancementDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import static de.itsgraphax.rmc5.RmcPlugin.rmc;

public class AdvancementListener implements Listener {
    @EventHandler
    void onAdvancement(PlayerAdvancementDoneEvent e) {
        AdvancementDisplay display = e.getAdvancement().getDisplay();
        if (display == null) return;

        if (display.frame() != AdvancementDisplay.Frame.CHALLENGE) return;

        e.getPlayer().give(rmc.cim
                .get(rmc.ns.itemNoxiumIngot())
                .createItem()
        );
    }
}
