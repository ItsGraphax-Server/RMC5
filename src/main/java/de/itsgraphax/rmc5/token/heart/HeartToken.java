package de.itsgraphax.rmc5.token.heart;

import de.itsgraphax.rmc5.misc.Utils;
import de.itsgraphax.rmc5.token.Token;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import de.itsgraphax.rmc5.token.TokenRarity;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static de.itsgraphax.rmc5.RmcPlugin.rmc;

public class HeartToken extends Token {
    private static AttributeModifier mod;

    private static PotionEffect resistance;

    public HeartToken() {
        super(TokenIdentifier.HEART, TokenRarity.MYTHIC);
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();

        mod = new AttributeModifier(
                rmc.ns.heartModifier(),
                config.getInt("heartMod"),
                AttributeModifier.Operation.ADD_NUMBER
        );
        resistance = new PotionEffect(
                PotionEffectType.RESISTANCE,
                5,
                config.getInt("resistanceAmplifier"));
    }

    @Override
    public void onEquip(Player p) {
        AttributeInstance attr = p.getAttribute(Attribute.MAX_HEALTH);
        assert attr != null;
        attr.addModifier(mod);
    }

    @Override
    public void onUnequip(Player p) {
        AttributeInstance attr = p.getAttribute(Attribute.MAX_HEALTH);
        assert attr != null;
        attr.removeModifier(mod);

        PotionEffect currentResistance = p.getPotionEffect(PotionEffectType.STRENGTH);
        if (currentResistance != null
                && currentResistance.getAmplifier() == resistance.getAmplifier()) {
            p.removePotionEffect(PotionEffectType.RESISTANCE);
        }
    }


    @EventHandler
    void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;
        if (isActive(p)) {
            p.getWorld().playSound(Sound.sound(new NamespacedKey("minecraft", "block.anvil.land"),
                    Sound.Source.MASTER, 0.4f, 1.0f), p);
        }
    }

    @Override
    public void onActiveTick(Player p) {
        p.addPotionEffect(resistance);

        if (rmc.getServer().getCurrentTick() % 5 == 0) {
            Utils.circleParticles(p.getLocation(), 1, 0.5f, Particle.DUST, new Particle.DustOptions(Color.BLACK, 1f));
        }
    }
}
