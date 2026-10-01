package de.itsgraphax.rmc5.misc;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

import static de.itsgraphax.rmc5.RmcPlugin.rmc;

public class Utils {
    public static <T> void circleParticles(Location oLoc, float radius, float spacing, Particle particleType, T data) {
        Location loc = oLoc.clone();
        loc.add(0, 0.1f, 0);
        World world = loc.getWorld();

        double scope = 2 * Math.PI * radius;
        int multiplier = (int) Math.round(scope / spacing);

        for (int i = 0; i < multiplier; i++) {
            double angle = 2 * Math.PI * i / multiplier;

            Location clone = loc.clone();
            clone.add(
                    Math.cos(angle) * radius,
                    0,
                    Math.sin(angle) * radius
            );

            world.spawnParticle(particleType, clone, 1, data);
        }
    }

    public static Audience getOnlineAudience() {
        return Audience.audience(Bukkit.getOnlinePlayers());
    }

    public static void editPlayerProfile(Player p, Consumer<PlayerProfile> c) {
        PlayerProfile profile = p.getPlayerProfile();
        c.accept(profile);
        p.setPlayerProfile(profile);
    }

    public static Component sourcePrefix(String spriteKey, Component suffix) {
        return rmc.rt.parse(String.format("[<sprite:items:rmc5/%s>] ", spriteKey)).append(suffix);
    }

    public static void sendSourcePrefixedMessage(String spriteKey, Component suffix) {
        getOnlineAudience().sendMessage(sourcePrefix(spriteKey, suffix));
    }
}
