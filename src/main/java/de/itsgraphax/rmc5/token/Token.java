package de.itsgraphax.rmc5.token;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityStatus;
import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.rmc5.HasPlugin;
import de.itsgraphax.rmc5.managers.PdcData;
import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Token extends Citem implements HasPlugin, Listener {
    private final TokenIdentifier id;
    private final TokenRarity rarity;
    protected ConfigurationSection config;

    public Token(TokenIdentifier id, TokenRarity rarity) {
        super(plugin.namespaces().itemToken(id));

        this.id = id;
        this.rarity = rarity;
        reloadConfig();
    }

    public void reloadConfig() {
        config = plugin.getConfig().getConfigurationSection("tokens." + id.id());
    }

    @Override
    public void onInteract(@NotNull PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        assert item != null;
        Player p = event.getPlayer();
        PdcData pdcData = plugin.getPdcData();

        boolean broken = pdcData.getItemBroken(item);
        int slot = getSlot();

        consume(event);

        // Unequip if there is already an equipped token
        TokenIdentifier currentlyEquipped = pdcData.getEquippedToken(p, slot);
        if (currentlyEquipped != TokenIdentifier.UNKNOWN) {
            plugin.tokenManager().unequipToken(p, slot);
        }

        pdcData.setEquippedToken(p, slot, id);
        pdcData.setEquippedBroken(p, slot, broken);


        if (!broken) onEquip(p);

        playTotemAnim(p, broken);
    }

    private void playTotemAnim(Player p, boolean broken) {
        WrapperPlayServerEntityStatus packet = new WrapperPlayServerEntityStatus(
                p.getEntityId(), 35
                );

        PlayerInventory pinv = p.getInventory();
        ItemStack hand = pinv.getItemInMainHand();
        ItemStack totem = ItemStack.of(Material.TOTEM_OF_UNDYING);
        totem.setData(DataComponentTypes.ITEM_MODEL, getKey(broken));
        pinv.setItemInMainHand(totem);

        PacketEvents.getAPI().getPlayerManager().sendPacket(p, packet);

        pinv.setItemInMainHand(hand);
    }


    public boolean hasWorkingToken(Player p) {
        for (int slot = 0; slot < 2; slot++) { // iterate over all slots
            if (
                    plugin.getPdcData().getEquippedToken(p, slot) == id &&
                            !plugin.getPdcData().getEquippedBroken(p, slot)
            ) return true;
        }
        return false;
    }

    @EventHandler
    private void onJoinListen(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (hasWorkingToken(p)) onJoin(p);
    }

    public void onEquip(Player p) {
    }

    public void onUnequip(Player p) {
    }

    public void onTick(Player p) {
    }

    public void onTrigger(Player p) {
    }

    public void onJoin(Player p) {
    }

    public int getBaseCooldown() {
        return config.getInt("cooldown");
    }

    public long secondsSinceLastUse(Player p) {
        LocalDateTime lastUse = plugin.getPdcData().getLastUse(p, id);
        int baseCooldown = getBaseCooldown();

        return Duration.between(lastUse, LocalDateTime.now()).toSeconds();
    }

    public boolean onCooldown(Player p) {
        return secondsSinceLastUse(p) < getBaseCooldown();
    }

    public String getCooldownString(Player p) {
        double total = getBaseCooldown();
        double elapsed = secondsSinceLastUse(p);

        double progress = Math.min(elapsed / total, 1.0);

        int filled = (int) Math.floor(progress * 5);

        return "■".repeat(filled) + "□".repeat(5 - filled);
    }

    protected int getSlot() {
        return switch (rarity) {
            case RARE, EPIC -> 0;
            case LEGENDARY, MYTHIC -> 1;
        };
    }

    public String getSprite(Player p, int slot) {
        String broken = plugin.getPdcData().getEquippedBroken(p, slot) ? "_broken" : "";
        return String.format("items:rmc5/token%s/%s", broken, id.id().toLowerCase());
    }

    public TokenIdentifier getId() {
        return id;
    }

    public TokenRarity getRarity() {
        return rarity;
    }

    public ConfigurationSection getConfig() {
        return config;
    }

    @Override
    public @NotNull ItemStack createItem() {
        return createItem(false);
    }

    private NamespacedKey getKey(boolean broken) {
        return new NamespacedKey(key.namespace(), key.getKey() + (broken ? "_broken" : ""));
    }

    public @NotNull ItemStack createItem(boolean broken) {
        ItemStack ret = super.createItem();
        plugin.getPdcData().setItemToken(ret, id);
        plugin.getPdcData().setItemBroken(ret, broken);

        NamespacedKey newKey = getKey(broken);

        ret.setData(DataComponentTypes.ITEM_MODEL, newKey);
        Component translation = Component.translatable(newKey.getNamespace() + "." + newKey.getKey())
                .decoration(TextDecoration.ITALIC, false);
        ret.setData(DataComponentTypes.ITEM_NAME, translation);
        return ret;
    }

    @Override
    public void setItem(@NotNull ItemStack item) {
        ItemStack copy = item.clone();
        super.setItem(copy);
    }
}
