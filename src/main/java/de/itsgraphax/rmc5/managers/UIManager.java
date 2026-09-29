package de.itsgraphax.rmc5.managers;

import de.itsgraphax.fusion.engine.text.positionedText.ComponentWidth;
import de.itsgraphax.fusion.engine.text.positionedText.Offset;
import de.itsgraphax.rmc5.events.EventIdentifier;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static de.itsgraphax.rmc5.RmcPlugin.rmc;

public class UIManager {
    public void render() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            render(p);
        }
    }

    private void render(Player p) {
        Component ui = Component.empty();
        EventIdentifier currentEvent = rmc.data.getCurrentEvent();

        // Token UI
        Component tokenUi = rmc.tokenManager.renderTokenUi(p);

        // Bounty UI
        if (currentEvent == EventIdentifier.BOUNTY) {
            Component coinUi = rmc.rt
                    .parse("<sprite:items:rmc5/misc/coins> {{VAL}}",
                            "VAL", String.valueOf(rmc.pdc.getBountyCoins(p)));

            int coinUiWidth = ComponentWidth.calculateWidth(coinUi);
            int tokenUiWidthHalf = ComponentWidth.calculateWidth(tokenUi) / 2;

            ui = ui
                    .append(Offset.createOffset(-90))
                    .append(coinUi)
                    .append(Offset.createOffset(90 - tokenUiWidthHalf - coinUiWidth))
                    .append(tokenUi)
                    .append(Offset.createOffset(-tokenUiWidthHalf));
        } else {
            ui = ui.append(tokenUi);
        }

        p.sendActionBar(ui);
    }
}
