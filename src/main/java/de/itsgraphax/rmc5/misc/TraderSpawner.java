package de.itsgraphax.rmc5.misc;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static de.itsgraphax.rmc5.RmcPlugin.rmc;

public class TraderSpawner {
    private static final Random rand = new Random();

    private static long setNextSpawn() {
        long nextSpawn = System.currentTimeMillis() + (rand.nextLong(
                rmc.getConfig().getLong("trader.min", 1),
                rmc.getConfig().getLong("trader.max", 2)
        ) * 1000 * 60); // 60 seconds = 1 minute , 1000 ms = 1 second

        rmc.data.setNextTrader(nextSpawn);
        return nextSpawn;
    }

    public static void tick() {
        spawnTraderTick();
        glowingTraderTick();
        killTraders();
    }

    private static void glowingTraderTick() {
        for (Entity eI : rmc.getServer().getRespawnWorld().getEntities()) {
            if (!(eI instanceof WanderingTrader e)) continue;

            e.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20, 0, true));
            if (rmc.getServer().getCurrentTick() % 20 == 0) {
                Utils.circleParticles(e.getLocation(), 0.5f, 0.5f, Particle.TOTEM_OF_UNDYING, null);
            }
        }
    }

    private static void spawnTraderTick() {
        Long nextSpawn = rmc.data.getNextTrader();
        if (nextSpawn == null) nextSpawn = setNextSpawn();

        if (System.currentTimeMillis() < nextSpawn) return;

        Location spawn = rmc.getServer().getRespawnWorld().getSpawnLocation();
        WanderingTrader trader = spawn.getWorld().spawn(spawn, WanderingTrader.class);

        Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
        audience.showTitle(Title.title(
                rmc.rt.translatable("trader.onspawn.title"),
                rmc.rt.translatable("trader.onspawn.subtitle")
        ));
        audience.playSound(Sound.sound(Key.key("totem_of_undying"), Sound.Source.MASTER, 1f, 1f), Sound.Emitter.self());

        setNextSpawn();
    }

    private static void killTraders() {
        boolean traderExists = false;
        for (Entity e : rmc.getServer().getRespawnWorld().getEntities()) {
            if (!(e instanceof WanderingTrader trader)) continue;

            // Ignore traders who have already been traded with
            if (trader.getRecipe(0).getUses() > 0) continue;

            // Remove exess traders
            if (traderExists) e.remove();
            else traderExists = true;
        }
    }
}
