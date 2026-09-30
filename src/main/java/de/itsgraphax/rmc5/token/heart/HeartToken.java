package de.itsgraphax.rmc5.token.heart;

import de.itsgraphax.rmc5.token.Token;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import de.itsgraphax.rmc5.token.TokenRarity;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HeartToken extends Token {
    private static AttributeModifier mod;

    private static PotionEffect effect;

    public HeartToken() {
        super(TokenIdentifier.HEART, TokenRarity.MYTHIC);

        mod = new AttributeModifier(
                rmc.ns().heartModifier(),
                config.getInt("heartMod"),
                AttributeModifier.Operation.ADD_NUMBER
        );
        effect = new PotionEffect(
                PotionEffectType.RESISTANCE,
                config.getInt("resistanceTime") * 20,
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
                && currentResistance.getAmplifier() == effect.getAmplifier()) {
            p.removePotionEffect(PotionEffectType.RESISTANCE);
        }
    }

    @Override
    public void onTrigger(Player p) {
        p.addPotionEffect(effect);
    }
}
