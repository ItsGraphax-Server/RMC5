package de.itsgraphax.rmc5.events.bounty;

import de.itsgraphax.fusion.engine.text.positionedText.ComponentWidth;
import de.itsgraphax.fusion.engine.text.positionedText.Offset;
import de.itsgraphax.rmc5.events.Event;
import de.itsgraphax.rmc5.events.EventIdentifier;
import de.itsgraphax.rmc5.misc.Utils;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Repairable;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

import static de.itsgraphax.rmc5.RmcPlugin.rmc;
import static java.util.Map.entry;

public class Bounty extends Event {
    private final Map<Player, Player> damagedToLastDamager = new HashMap<>();

    public Bounty() {
        super(EventIdentifier.BOUNTY);
    }

    private static class BountyCalculation {
        private static final Random rand = new Random();

        private static final Map<ItemType, Integer> armorToPoints = Map.ofEntries(
                entry(ItemType.AIR, -2),
                entry(ItemType.CARVED_PUMPKIN, 4),
                entry(ItemType.LEATHER, 2),
                entry(ItemType.COPPER_INGOT, 2),
                entry(ItemType.IRON_INGOT, 6),
                entry(ItemType.GOLD_INGOT, 4),
                entry(ItemType.DIAMOND, 10),
                entry(ItemType.NETHERITE_INGOT, 12)
        );

        private static int getIndividualArmorPoints(ItemStack item) {
            Material type = item.getType();

            ItemType repair;
            // Case: None
            if (type == Material.AIR) {
                repair = ItemType.AIR;
            } else {
                Repairable repairComponent = item.getData(DataComponentTypes.REPAIRABLE);

                // Case: Other, non repairable
                if (repairComponent == null) {
                    repair = ItemType.CARVED_PUMPKIN;
                } else {
                    var typeRegistry = RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM);

                    // Case (will be overridden if other case happens): Other, repairable but unknown
                    repair = ItemType.CARVED_PUMPKIN;
                    for (var typeKey : repairComponent.types().values()) {
                        ItemType itemType = typeRegistry.get(typeKey);

                        // Case: Known repairing item
                        if (armorToPoints.containsKey(itemType)) {
                            repair = itemType;
                            break;
                        }
                    }
                }
            }

            return armorToPoints.get(repair);
        }

        private static int getArmorPoints(Player p) {
            PlayerInventory inv = p.getInventory();
            return
                    getIndividualArmorPoints(inv.getHelmet()) +
                            getIndividualArmorPoints(inv.getLeggings()) +
                            getIndividualArmorPoints(inv.getChestplate()) +
                            getIndividualArmorPoints(inv.getHelmet());
        }

        public static double calculateBaseBounty(Player p) {
            double bounty = 0;

            bounty += p.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20f / 60f / 60f
                    * config.getDouble("bountyMultipliers.playtimeHours");
            bounty += getArmorPoints(p)
                    * config.getDouble("bountyMultipliers.armor");
            bounty += p.getStatistic(Statistic.PLAYER_KILLS)
                    * config.getDouble("bountyMultipliers.kills");
            bounty += p.getStatistic(Statistic.DEATHS)
                    * config.getDouble("bountyMultipliers.deaths");
            bounty += rmc.pdc.getBountyCoins(p)
                    * config.getDouble("bountyMultipliers.coins");

            return Math.clamp(bounty,
                    100, Integer.MAX_VALUE);
        }

        public static int calculateRandomizedBounty(Player p) {
            double base = calculateBaseBounty(p);
            double randMultiplier = rand.nextDouble(
                    config.getDouble("bountyMultipliers.minRand"),
                    config.getDouble("bountyMultipliers.maxRand"));

            return (int) Math.round(base * randMultiplier);
        }
    }


    private void newBounty(Player p) {
        Integer currentCycle = rmc.data.getBountyCycle();
        Integer playerCycle = rmc.pdc.getBountyLastUpdateCycle(p);

        if (currentCycle == null || currentCycle.equals(playerCycle)) return;

        rmc.pdc.setBountyBounty(p, BountyCalculation.calculateRandomizedBounty(p));
        rmc.pdc.setBountyClaims(p, 0);
        rmc.pdc.setBountyLastUpdateCycle(p, currentCycle);
        updateTablistName(p);
    }

    public void updateTablistName(Player p) {
        if (isEventRunning()) {
            // Bounty UI
            Component bountyUi = rmc.rt
                    .parse("<sprite:items:rmc5/misc/bounty> {{VAL}}",
                            "VAL", String.valueOf(rmc.pdc.getBountyBounty(p)));

            // Offset
            int playernameWidth = ComponentWidth.calculateWidth(p.name());
            Component offset = Offset.createOffset(97 - playernameWidth);

            // Player Name + Offset + Bounty UI
            p.playerListName(p.name().append(offset).append(bountyUi));
        } else {
            p.playerListName(null);
        }
    }


    @Override
    public void onStart() {
        int lastCycle = rmc.data.getBountyCycle();
        rmc.data.setBountyCycle(lastCycle + 1);

        for (Player p : rmc.getServer().getOnlinePlayers()) {
            newBounty(p);
        }
    }

    @Override
    public void onEnd() {
        for (Player p : rmc.getServer().getOnlinePlayers()) {
            updateTablistName(p);
        }
    }


    @EventHandler
    void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        newBounty(player);
        // TODO: This might break other things modifying the tablist. Maybe move to UiManager?
        updateTablistName(player);
    }


    @EventHandler
    void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamageSource().getCausingEntity() instanceof Player damager) ||
        !(e.getEntity() instanceof Player damaged)) return;

        damagedToLastDamager.put(damaged, damager);
    }

    @EventHandler
    void onResurrect(EntityResurrectEvent e) {
        if (!(e.getEntity() instanceof Player killed)) return;
        if (!isEventRunning()) return;

        int bounty = rmc.pdc.getBountyBounty(killed);
        if (bounty == 0) return;

        // get killer
        Player killer = damagedToLastDamager.get(killed);
        if (killer == null) {

            Utils.sendSourcePrefixedMessage("misc/bounty", rmc.rt.parse("<red>No player could be found to be the last attacker of {{PLAYER}}.",
                    "PLAYER", killed.getName()));
            return;

        }
        if (killed == killer) {

            Utils.sendSourcePrefixedMessage("misc/bounty", rmc.rt.parse("<red>{{PLAYER}} has just tried to claim their own bounty 💀",
                    "PLAYER", killed.getName()));
            return;

        }

        int bountyClaims = rmc.pdc.getBountyClaims(killer);
        if (bountyClaims >= config.getInt("maxClaims", 3)) {

            Utils.sendSourcePrefixedMessage("misc/bounty", rmc.rt.parse("<red>{{KILLER}} could not claim {{KILLED}}'s bounty due to already having claimed a bounty.",
                    "KILLER", killer.getName(),
                    "KILLED", killed.getName()));
            return;

        }
        rmc.pdc.setBountyClaims(killer, bountyClaims + 1);

        // activate totem
        if (!e.isCancelled()) { // player has a totem if not cancelled
            EquipmentSlot hand = e.getHand();
            assert hand != null;
            killed.getInventory().setItem(hand, ItemStack.of(Material.TOTEM_OF_UNDYING));
        }
        else {
            e.setCancelled(false);
        }


        // do bounty
        rmc.pdc.setBountyBounty(killed, 0);

        int oldKillerCoins = rmc.pdc.getBountyCoins(killer);
        rmc.pdc.setBountyCoins(killer, oldKillerCoins + bounty);

        int oldKilledCoins = rmc.pdc.getBountyCoins(killed);
        rmc.pdc.setBountyCoins(killed, oldKilledCoins - bounty / 2);

        Utils.sendSourcePrefixedMessage("misc/bounty",
                rmc.rt.parse("<yellow>{{KILLER}} has claimed {{KILLED}}'s bounty of {{BOUNTY}}!")
        );
        killed.getWorld().playSound(Sound.sound(Key.key("ambient.weather.thunder"),
                Sound.Source.MASTER, 2, 1), killed);

        updateTablistName(killed);
    }
}
