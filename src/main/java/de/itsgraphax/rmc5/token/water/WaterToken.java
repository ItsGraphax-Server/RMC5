package de.itsgraphax.rmc5.token.water;

import de.itsgraphax.rmc5.misc.Utils;
import de.itsgraphax.rmc5.token.Token;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import de.itsgraphax.rmc5.token.TokenRarity;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import static de.itsgraphax.rmc5.RmcPlugin.rmc;

public class WaterToken extends Token {
    protected static double middleRingRadius;
    protected static double topRingRadius;
    protected static double radius;

    static final PotionEffect dolphinsGrace = new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20, 0, true);

    public WaterToken() {
        super(TokenIdentifier.WATER, TokenRarity.RARE);
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        radius = config.getDouble("radius", 4.0);
        middleRingRadius = Math.sqrt(Math.pow(radius, 2) - Math.pow(radius/2, 2)); // Pythagoras :D
        topRingRadius = Math.sqrt(Math.pow(radius, 2) - Math.pow(radius/5*4, 2));
    }

    protected void makeAquaDustCircle(Location loc, double radius) {
        Utils.circleParticles(loc, (float) radius, 1, Particle.DUST, new Particle.DustOptions(Color.BLUE, 1));
    }

    @Override
    public void onActiveTick(Player p) {
        Location center = p.getLocation();
        double strength = config.getDouble("strength", 0.8);

        if (rmc.getServer().getCurrentTick() % 40 == 0) {
            center.getWorld().playSound(Sound.sound(
                    Key.key("entity.guardian.elder_idle"),
                    Sound.Source.MASTER,
                    1f, 1f
            ), p);
        }

        makeAquaDustCircle(center, radius);

        makeAquaDustCircle(center.add(0, radius/2, 0), middleRingRadius);
        makeAquaDustCircle(center.subtract(0, radius/2, 0), middleRingRadius);

        makeAquaDustCircle(center.add(0, radius/5*4, 0), topRingRadius);
        makeAquaDustCircle(center.subtract(0, radius/5*4, 0), topRingRadius);

        for (LivingEntity entity : p.getWorld().getNearbyLivingEntities(center, radius)) {
            if (entity.equals(p)) continue;

            Location entityLocation = entity.getLocation();
            Vector direction = entityLocation.toVector().subtract(center.toVector());
            double distance = direction.length();

            direction.normalize();

            double force = strength * (radius - distance); // stronger the closer
            Vector velocity = direction.multiply(force);
            velocity.setY(Math.max(velocity.getY(), 0.15));

            entity.setVelocity(velocity);
        }
    }

    @Override
    public void onTick(Player p) {
        p.addPotionEffect(dolphinsGrace);
        p.setRemainingAir(200);
    }

    @Override
    public void onTrigger(Player p) {
        p.getWorld().playSound(Sound.sound(Key.key("sounds.minecart.inside_underwater"),
                Sound.Source.MASTER, 1, 1), p);
    }
}
