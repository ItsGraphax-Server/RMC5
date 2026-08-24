package de.itsgraphax.rmc5.misc;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class DisableFireAspectListener implements Listener {
    @EventHandler()
    void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player attacked &&
                e.getDamageSource().getCausingEntity() instanceof Player attacker)) return;

        ItemStack mainhand = attacker.getInventory().getItemInMainHand();
        if (mainhand.getEnchantmentLevel(Enchantment.FIRE_ASPECT) == 0) return;

        attacked.setFireTicks(0);
    }
}
