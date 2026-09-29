package de.itsgraphax.rmc5.token;

import de.itsgraphax.rmc5.HasPlugin;
import de.itsgraphax.rmc5.managers.PdcData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;

public class TokenListener implements HasPlugin, Listener {
    @EventHandler
    void onDeath(PlayerDeathEvent e) {
        PdcData pdcData = rmc.pdc();
        Player p = e.getPlayer();
        for (int slot = 0; slot < 2; slot++) { // loop over slots
            Token token = rmc.tokenManager().tokenFromId(pdcData.getEquippedToken(p, slot));
            if (token.getId() != TokenIdentifier.UNKNOWN &&
            !pdcData.getEquippedBroken(p, slot)) {
                pdcData.setEquippedBroken(e.getPlayer(), slot, true);
                token.onUnequip(p);
            }
        }
    }

    @EventHandler
    void onCraft(CraftItemEvent e) {
        Token token = rmc.tokenManager().tokenFromItem(e.getRecipe().getResult());
        if (!(e.getView().getPlayer() instanceof Player p)) return;
        if (token.getRarity() != TokenRarity.EPIC) return;

        int tokenCrafts = rmc.data().getCrafts(token.getId());
        int playerCrafts = rmc.pdc().getRareCrafts(p);

        if (tokenCrafts >= token.getConfig().getInt("maxCrafts", 3) ||
                playerCrafts >= rmc.getConfig().getInt("maxRareCrafts", 1)) {
            p.sendMessage(rmc.rt().translatable("error.maxCrafts"));
            e.setCancelled(true);
            return;
        }


        rmc.data().setCrafts(token.getId(), tokenCrafts + 1);
        rmc.pdc().setRareCrafts(p, playerCrafts + 1);
    }
}
