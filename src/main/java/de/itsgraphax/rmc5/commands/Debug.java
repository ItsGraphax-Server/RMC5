package de.itsgraphax.rmc5.commands;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.rmc5.HasPlugin;
import de.itsgraphax.rmc5.commands.suggestions.customItem.CitemSuggestions;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.Subcommand;
import net.strokkur.commands.arguments.StringArg;
import net.strokkur.commands.arguments.StringArgType;
import net.strokkur.commands.paper.Executor;
import net.strokkur.commands.paper.RequiresOP;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("rmc")
@RequiresOP
public class Debug implements HasPlugin {
    @Subcommand("give")
    static class GiveSub {
        @Executes
        void giveToken(CommandSender sender, @CitemSuggestions @StringArg(StringArgType.STRING) String ns, Player p) {
            NamespacedKey key = NamespacedKey.fromString(ns);
            if (key == null) {
                sender.sendMessage(plugin.richText().parse("<red>This is an invalid namespace!"));
                return;
            }

            Citem citem = plugin.citemManager().get(key);
            if (citem == null) {
                sender.sendMessage(plugin.richText().parse("<red>This citem does not exist!"));
                return;
            }

            p.give(citem.createItem());
            sender.sendMessage(plugin.richText().parse("<green>Gave you the citem!"));
        }

        @Executes
        void giveToken(@Executor Player sender, @CitemSuggestions @StringArg(StringArgType.STRING) String token) {
            giveToken(sender, token, sender);
        }
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
}
