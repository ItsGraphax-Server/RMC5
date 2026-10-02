package de.itsgraphax.rmc5.commands;

import de.itsgraphax.rmc5.token.TokenIdentifier;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.paper.Description;
import net.strokkur.commands.paper.Executor;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;

import static de.itsgraphax.rmc5.RmcPlugin.rmc;

@Command("token")
@Description("Triggers a token")
public class Token {
    @Executes("left")
    void triggerLeft(@Executor Player p) {
        trigger(p, 0);
    }

    @Executes("right")
    void triggerRight(@Executor Player p) {
        trigger(p, 1);
    }

    final void trigger(Player p, int slot) {
        TokenIdentifier id = rmc.pdc.getEquippedToken(p, slot);
        boolean broken = rmc.pdc.getEquippedBroken(p, slot);
        if (id == TokenIdentifier.UNKNOWN) {
            p.sendMessage(rmc.rt.translatable("command.token.noToken"));
            return;
        }
        if (broken) {
            p.sendMessage(rmc.rt.translatable("command.token.broken"));
            return;
        }

        de.itsgraphax.rmc5.token.Token token = rmc.tokenManager.tokenFromId(id);

        if (token.onCooldown(p)) {
            p.sendMessage(rmc.rt.translatable("command.token.onCooldown"));
            return;
        }

        token.onTrigger(p);

        rmc.pdc.setLastUse(p, id, LocalDateTime.now());
    }
}
