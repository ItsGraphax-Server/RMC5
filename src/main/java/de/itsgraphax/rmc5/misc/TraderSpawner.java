package de.itsgraphax.rmc5.misc;

import de.itsgraphax.rmc5.HasPlugin;
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

public class TraderSpawner implements HasPlugin {
    private static final Random rand = new Random();

    private static long setNextSpawn() {
        long nextSpawn = System.currentTimeMillis() + (rand.nextLong(
                plugin.getConfig().getLong("trader.min", 1),
                plugin.getConfig().getLong("trader.max", 2)
        ) * 1000 * 60); // 60 seconds = 1 minute , 1000 ms = 1 second

        plugin.getDataManager().setNextTrader(nextSpawn);
        return nextSpawn;
    }

    public static void tick() {
        spawnTraderTick();
        glowingTraderTick();
    }

    private static void glowingTraderTick() {
        for (Entity eI : plugin.getServer().getRespawnWorld().getEntities()) {
            if (!(eI instanceof WanderingTrader e)) continue;

            e.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20, 0, true));
            if (plugin.getServer().getCurrentTick() % 20 == 0) {
                Utils.circleParticles(e.getLocation(), 0.5f, 0.5f, Particle.TOTEM_OF_UNDYING, null);
            }
        }
    }

    private static void spawnTraderTick() {
        Long nextSpawn = plugin.getDataManager().getNextTrader();
        if (nextSpawn == null) nextSpawn = setNextSpawn();

        if (System.currentTimeMillis() < nextSpawn) return;

        Location spawn = plugin.getServer().getRespawnWorld().getSpawnLocation();
        WanderingTrader e = spawn.getWorld().spawn(spawn, WanderingTrader.class);

        Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
        audience.showTitle(Title.title(
                plugin.richText().translatable("titles.traderSpawned"),
                plugin.richText().translatable("subtitles.traderSpawned")
        ));
        audience.playSound(Sound.sound(Key.key("totem_of_undying"), Sound.Source.MASTER, 1f, 1f), Sound.Emitter.self());

        setNextSpawn();
    }
}
