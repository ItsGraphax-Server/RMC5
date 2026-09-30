package de.itsgraphax.rmc5.commands;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.rmc5.commands.suggestions.customItem.CitemSuggestions;
import de.itsgraphax.rmc5.events.EventIdentifier;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.Subcommand;
import net.strokkur.commands.paper.Executor;
import net.strokkur.commands.paper.RequiresOP;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.itsgraphax.rmc5.RmcPlugin.rmc;

@Command("rmc")
@RequiresOP
public class Debug {
    @Executes("give")
    void give(@Executor Player sender, @CitemSuggestions NamespacedKey key) {
        if (key == null) {
            sender.sendMessage(rmc.rt.parse("<red>This is an invalid namespace!"));
            return;
        }

        Citem citem = rmc.cim.get(key);
        if (citem == null) {
            sender.sendMessage(rmc.rt.parse("<red>This citem does not exist!"));
            return;
        }

        sender.give(citem.createItem());
        sender.sendMessage(rmc.rt.parse("<green>Gave you the citem!"));
    }

    @Executes("reload")
    void reload(CommandSender sender) {
        rmc.saveDefaultConfig();

        rmc.reloadConfig();
        rmc.tokenManager.reloadConfig();
        rmc.eventManager.reloadConfig();

        sender.sendMessage(rmc.rt.parse("<green>Successfully reloaded config!"));
    }

    @Executes("reset")
    void reset(Player p) {
        rmc.tokenManager.reset(p);
    }

    @Subcommand("trader")
    static class TraderSub {
        @Executes("getTime")
        void getTime(CommandSender sender) {
            Long next = rmc.data.getNextTrader();
            if (next == null) {
                sender.sendMessage("null");
                return;
            }
            sender.sendMessage(String.valueOf((next - System.currentTimeMillis()) / 1000));
        }

        @Executes("setTime")
        void setTime(int seconds) {
            rmc.data.setNextTrader(System.currentTimeMillis() + (seconds * 1000L));
        }
    }

    @Subcommand("bounty")
    static class BountySub {
        @Subcommand("coins")
        static class BountyCoinsSub {
            @Executes("set")
            void setCoins(Player p, int amount) {
                rmc.pdc.setBountyCoins(p, amount);
            }

            @Executes("get")
            void getCoins(CommandSender s, Player p) {
                s.sendMessage(String.valueOf(rmc.pdc.getBountyCoins(p)));
            }
        }

        @Subcommand("bounty")
        static class BountyBountySub {
            @Executes("set")
            void setBounty(Player p, int val) {
                rmc.pdc.setBountyBounty(p, val);
            }

            @Executes("get")
            void getBounty(CommandSender s, Player p) {
                s.sendMessage(String.valueOf(rmc.pdc.getBountyBounty(p)));
            }
        }
    }

    @Subcommand("event")
    static class EventSub {
        @Executes("set")
        void setEvent(String id) {
            rmc.data.setCurrentEvent(EventIdentifier.valueOf(id));
        }
    }
}
