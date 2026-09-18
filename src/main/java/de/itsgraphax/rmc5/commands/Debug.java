package de.itsgraphax.rmc5.commands;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.rmc5.HasPlugin;
import de.itsgraphax.rmc5.commands.suggestions.customItem.CitemSuggestions;
import de.itsgraphax.rmc5.events.EventIdentifier;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.paper.Executor;
import net.strokkur.commands.paper.RequiresOP;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("rmc")
@RequiresOP
public class Debug implements HasPlugin {
    @Executes("give")
    void giveToken(@Executor Player sender, @CitemSuggestions NamespacedKey key) {
        if (key == null) {
            sender.sendMessage(plugin.richText().parse("<red>This is an invalid namespace!"));
            return;
        }

        Citem citem = plugin.citemManager().get(key);
        if (citem == null) {
            sender.sendMessage(plugin.richText().parse("<red>This citem does not exist!"));
            return;
        }

        sender.give(citem.createItem());
        sender.sendMessage(plugin.richText().parse("<green>Gave you the citem!"));
    }

    @Executes("reload")
    void reload(CommandSender sender) {
        plugin.saveDefaultConfig();

        plugin.reloadConfig();
        plugin.tokenManager().reloadConfig();

        sender.sendMessage(plugin.richText().parse("<green>Successfully reloaded config!"));
    }

    @Executes("reset")
    void reset(Player p) {
        plugin.tokenManager().reset(p);
    }

    @Executes("getTraderTime")
    void getTraderTime(CommandSender sender) {
        Long next = plugin.getDataManager().getNextTrader();
        if (next == null) {
            sender.sendMessage("null");
            return;
        }
        sender.sendMessage(String.valueOf((next - System.currentTimeMillis()) / 1000));
    }

    @Executes("setTraderTime")
    void setTraderTime(int seconds) {
        plugin.getDataManager().setNextTrader(System.currentTimeMillis() + (seconds * 1000L));
    }

    @Executes("setEvent")
    void setEvent(CommandSender sender, String id) {
        plugin.getDataManager().setCurrentEvent(EventIdentifier.valueOf(id));
    }
}
