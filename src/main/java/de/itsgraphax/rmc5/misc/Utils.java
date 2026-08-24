package de.itsgraphax.rmc5.misc;

import de.itsgraphax.rmc5.HasPlugin;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class Utils implements HasPlugin {
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


}
