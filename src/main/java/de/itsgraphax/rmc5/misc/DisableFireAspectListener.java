package de.itsgraphax.rmc5.misc;

import de.itsgraphax.rmc5.HasPlugin;
import de.itsgraphax.rmc5.RmcPlugin;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class DisableFireAspectListener implements Listener, HasPlugin {

    @EventHandler()
    void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player attacked &&
                e.getDamageSource().getCausingEntity() instanceof Player attacker)) return;
        ItemStack mainhand = attacker.getInventory().getItemInMainHand();
        if (mainhand.getEnchantmentLevel(Enchantment.FIRE_ASPECT) == 0) return;
        System.out.println("bunsssss");
        Bukkit.getScheduler().runTaskLater(RmcPlugin.instance(), () -> attacked.setFireTicks(0), 2);
    }
}
