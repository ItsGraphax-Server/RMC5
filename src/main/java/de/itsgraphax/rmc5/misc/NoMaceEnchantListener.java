package de.itsgraphax.rmc5.misc;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class NoMaceEnchantListener implements Listener {
    @EventHandler
    void onEnchant(EnchantItemEvent e) {
        if (e.getItem().getType() != Material.MACE) return;

        e.setCancelled(true);
    }
    @EventHandler
    void onAnvil(PrepareAnvilEvent e) {
        if (e.getResult() == null ||
        e.getResult().getType() != Material.MACE || // Skip if not a mace
        e.getResult().getEnchantments().isEmpty()) return; // Or item has no enchants

        e.setResult(null);
    }
}
