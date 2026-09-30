package de.itsgraphax.rmc5.token.crab;

import de.itsgraphax.rmc5.misc.Utils;
import de.itsgraphax.rmc5.token.Token;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import de.itsgraphax.rmc5.token.TokenRarity;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class CrabToken extends Token {
    private AttributeModifier mod;

    public CrabToken() {
        super(TokenIdentifier.CRAB, TokenRarity.EPIC);
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        mod = new AttributeModifier(rmc.ns().crabModifier(),
                config.getDouble("rangeMultiplier"), AttributeModifier.Operation.MULTIPLY_SCALAR_1);
    }

    @Override
    public void onEquip(Player p) {
        AttributeInstance att = p.getAttribute(Attribute.BLOCK_INTERACTION_RANGE);
        assert att != null;
        att.addModifier(mod);
    }

    @Override
    public void onUnequip(Player p) {
        AttributeInstance att = p.getAttribute(Attribute.BLOCK_INTERACTION_RANGE);
        assert att != null;
        att.removeModifier(mod);
    }

    @EventHandler
    void onItemDamage(PlayerItemDamageEvent e) {
        if (!hasWorkingToken(e.getPlayer())) return;
        if (!config.getStringList("appliesTo").contains(e.getItem().getType().toString())) return;

        e.setCancelled(true);
    }

    @Override
    public void onTrigger(Player p) {
        Utils.getOnlineAudience().playSound(Sound.sound(Key.key("block.enderchest.open"),
                Sound.Source.MASTER, 0.75f, 1.0f), p);

        p.openInventory(p.getEnderChest());
    }
}
