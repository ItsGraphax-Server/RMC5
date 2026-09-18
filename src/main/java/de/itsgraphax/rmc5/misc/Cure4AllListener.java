package de.itsgraphax.rmc5.misc;

import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.UUID;

public class Cure4AllListener implements Listener {
    @EventHandler
    void onEntity(PlayerInteractAtEntityEvent e) {
        if (!(e.getRightClicked() instanceof Villager vill)) return;

        UUID uuid = e.getPlayer().getUniqueId();
        Reputation pRep = vill.getReputation(uuid);
        if (pRep.hasReputationSet(ReputationType.MAJOR_POSITIVE)) return;

        for (Reputation rep : vill.getReputations().values()) {
            if (rep.getReputation(ReputationType.MAJOR_POSITIVE) > 0) {
                pRep.setReputation(ReputationType.MAJOR_POSITIVE, 20);
                vill.setReputation(uuid, pRep);
                return;
            }
        }
    }
}
