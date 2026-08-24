package de.itsgraphax.rmc5.token;

import de.itsgraphax.rmc5.HasPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;

public class TokenListener implements HasPlugin, Listener {
    @EventHandler
    void onDeath(PlayerDeathEvent e) {
        for (int slot = 0; slot < 2; slot++) { // loop over slots
            if (plugin.getPdcData().getEquippedToken(e.getPlayer(), slot) != TokenIdentifier.UNKNOWN) {
                plugin.getPdcData().setEquippedBroken(e.getPlayer(), slot, true);
            }
        }
    }

    @EventHandler
    void onCraft(CraftItemEvent e) {
        Token token = plugin.tokenManager().tokenFromItem(e.getRecipe().getResult());
        if (!(e.getView().getPlayer() instanceof Player p)) return;
        if (token.getRarity() != TokenRarity.EPIC) return;

        int tokenCrafts = plugin.getDataManager().getCrafts(token.getId());
        int playerCrafts = plugin.getPdcData().getRareCrafts(p);
        if (tokenCrafts <= token.getConfig().getInt("maxCrafts", 1) ||
                playerCrafts >= plugin.getConfig().getInt("maxRareCrafts", 3)) {
            //p.sendMessage(plugin.richText().translatable("error.maxCrafts"));
            //e.setCancelled(true);
            //return;
        }


        plugin.getDataManager().setCrafts(token.getId(), tokenCrafts + 1);
        plugin.getPdcData().setRareCrafts(p, playerCrafts + 1);
    }
}
