package de.itsgraphax.rmc5.customItems.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import static de.itsgraphax.rmc5.RmcPlugin.rmc;

public class DolphinFinListener implements Listener {
    @EventHandler
    void onDolphinDeath(EntityDeathEvent e) {
        if (e.getEntityType() != EntityType.DOLPHIN) return;

        e.getDrops().add(rmc.cim.get(rmc.ns.itemDolphinFin()).createItem());
    }
}
