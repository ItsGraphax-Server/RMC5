package de.itsgraphax.rmc5.managers;

import de.itsgraphax.fusion.engine.text.positionedText.ComponentWidth;
import de.itsgraphax.fusion.engine.text.positionedText.Offset;
import de.itsgraphax.rmc5.HasPlugin;
import de.itsgraphax.rmc5.events.EventIdentifier;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UIManager implements HasPlugin {
    public void render() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            render(p);
        }
    }

    private void render(Player p) {
        Component ui = Component.empty();
        EventIdentifier currentEvent = plugin.getDataManager().getCurrentEvent();

        // Token UI
        Component tokenUi = plugin.tokenManager().renderTokenUi(p);

        // Bounty UI
        if (currentEvent == EventIdentifier.BOUNTY) {
            Component bountyUi = plugin.richText()
                    .parse("<sprite:items:rmc5/misc/bounty> {{VAL}}",
                    "VAL", String.valueOf(plugin.getPdcData().getBountyWallet(p)));

            int bountyUiWidth = ComponentWidth.calculateWidth(bountyUi);
            int tokenUiWidthHalf = ComponentWidth.calculateWidth(tokenUi) / 2;

            ui = ui
                    .append(Offset.createOffset(-85))
                    .append(bountyUi)
                    .append(Offset.createOffset(85 - tokenUiWidthHalf - bountyUiWidth))
                    .append(tokenUi)
                    .append(Offset.createOffset(-tokenUiWidthHalf));
        } else {
            ui = ui.append(tokenUi);
        }

        p.sendActionBar(ui);
    }
}
