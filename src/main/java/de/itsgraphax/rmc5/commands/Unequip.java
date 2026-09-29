package de.itsgraphax.rmc5.commands;

import de.itsgraphax.rmc5.HasPlugin;
import de.itsgraphax.rmc5.token.TokenManager;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.paper.Description;
import net.strokkur.commands.paper.Executor;
import org.bukkit.entity.Player;

@Command("unequip")
@Description("Unequips a token")
public class Unequip implements HasPlugin {
    @Executes("left")
    void unequipLeft(@Executor Player p) {
        unequip(p, 0);
    }
    @Executes("right")
    void unequipRight(@Executor Player p) {
        unequip(p, 1);
    }

    void unequip(Player p, int slot) {
        TokenManager.UnequipResult result = rmc.tokenManager().unequipToken(p, slot);

        String translationKey = switch (result) {
            case UNEQUIPPED -> "command.unequip.unequipped";
            case NO_TOKEN -> "command.unequip.noToken";
        };
        p.sendMessage(rmc.rt().translatable(translationKey));
    }
}
