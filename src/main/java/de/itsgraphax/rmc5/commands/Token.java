package de.itsgraphax.rmc5.commands;

import de.itsgraphax.rmc5.HasPlugin;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.paper.Description;
import net.strokkur.commands.paper.Executor;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;

@Command("token")
@Description("Triggers a token")
public class Token implements HasPlugin {
    @Executes("left")
    void triggerLeft(@Executor Player p) {
        trigger(p, 0);
    }
    @Executes("right")
    void triggerRight(@Executor Player p) {
        trigger(p, 1);
    }

    void trigger(Player p, int slot) {
        TokenIdentifier id = plugin.pdcData().getEquippedToken(p, slot);
        boolean broken = plugin.pdcData().getEquippedBroken(p, slot);
        if (id == TokenIdentifier.UNKNOWN) {
            p.sendMessage(plugin.richText().translatable("command.trigger.noToken"));
            return;
        }
        if (broken) {
            p.sendMessage(plugin.richText().translatable("command.trigger.broken"));
            return;
        }

        de.itsgraphax.rmc5.token.Token token = plugin.tokenManager().tokenFromId(id);

        if (token.onCooldown(p)) {
            p.sendMessage(plugin.richText().translatable("command.trigger.onCooldown"));
            return;
        }

        token.onTrigger(p);

        plugin.pdcData().setLastUse(p, id, LocalDateTime.now());
    }
}
