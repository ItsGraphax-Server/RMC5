package de.itsgraphax.rmc5.token.fire;

import de.itsgraphax.rmc5.token.Token;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import de.itsgraphax.rmc5.token.TokenRarity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class FireToken extends Token {
    public FireToken() {
        super(TokenIdentifier.FIRE, TokenRarity.EPIC);
    }

    @Override
    public void onTick(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20, 0));
    }

    @Override
    public void onTrigger(Player p) {
        double strength = config.getDouble("strength", 1.0);
        double speed = config.getDouble("speed", 10.0);

        Fireball fireball = p.launchProjectile(Fireball.class);

        Vector direction = p.getLocation().getDirection().normalize();

        fireball.setAcceleration(direction.multiply(speed));
        fireball.setYield((float) strength);
    }
}
