package de.itsgraphax.rmc5.token.air;

import de.itsgraphax.rmc5.misc.Utils;
import de.itsgraphax.rmc5.token.Token;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import de.itsgraphax.rmc5.token.TokenRarity;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AirToken extends Token {
    private static final Map<UUID, Float> lastFallDistance = new HashMap<>();

    public AirToken() {
        super(TokenIdentifier.AIR, TokenRarity.RARE);
    }

    private static final AttributeModifier mod = new AttributeModifier(rmc.ns.airModifier(),
            -1, AttributeModifier.Operation.MULTIPLY_SCALAR_1);

    @Override
    public void onEquip(Player p) {
        AttributeInstance att = p.getAttribute(Attribute.FALL_DAMAGE_MULTIPLIER);
        assert att != null;
        att.addModifier(mod);
    }

    @Override
    public void onUnequip(Player p) {
        AttributeInstance att = p.getAttribute(Attribute.FALL_DAMAGE_MULTIPLIER);
        assert att != null;
        att.removeModifier(mod);
    }

    private void particles(Location loc, float spacing) {
        Utils.circleParticles(loc, 1f, 0.5f,
                Particle.DUST, new Particle.DustOptions(Color.WHITE, 2f));
    }

    @Override
    public void onTick(Player p) {
        UUID uuid = p.getUniqueId();

        // When falling more than 3 blocks
        if (p.getFallDistance() == 0 &&
                lastFallDistance.getOrDefault(uuid, 0F) > 3) {
            particles(p.getLocation(), 1);
        }

        lastFallDistance.put(uuid, p.getFallDistance());
    }


    private static void launchForward(Player player, double strength) {
        Vector dir = player.getLocation().getDirection();
        dir.multiply(strength);
        dir.add(new Vector(0, 0.5, 0)); // so one dosent get stuck to a 1block wall
        player.setVelocity(dir);
    }


    @Override
    public void onTrigger(Player p) {
        // tp up to remove floor drag
        Location oLoc = p.getLocation();
        oLoc.add(0, 0.5, 0);
        p.teleport(oLoc, PlayerTeleportEvent.TeleportCause.PLUGIN);

        Vector dir = p.getLocation().getDirection();
        dir.multiply(config.getDouble("multiplier"));
        dir.add(new Vector(0, config.getDouble("lift"), 0));
        p.setVelocity(dir);

        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BREEZE_SHOOT, 1, 1);
        particles(p.getLocation(), 0.3f);
    }
}
