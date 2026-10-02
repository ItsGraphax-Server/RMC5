package de.itsgraphax.rmc5.token.warden;

import de.itsgraphax.rmc5.token.Token;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import de.itsgraphax.rmc5.token.TokenRarity;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

public class WardenToken extends Token {
    private final ArenaWorldManager worldManager = new ArenaWorldManager();

    private final Set<Location> placedBlocks = new HashSet<>();
    private boolean dimensionActive = false;

    public WardenToken() {
        super(TokenIdentifier.WARDEN, TokenRarity.EPIC);
    }

    @EventHandler
    void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player p && event.getEntity() instanceof LivingEntity entity) {
            if (!hasWorkingToken(p)) return;
            if (Math.random() > config.getDouble("chance", 0.05)) return;

            entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_CREAKING_ATTACK, SoundCategory.MASTER, 2f, 1f);
            entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, config.getInt("duration") * 20, 4));
            entity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, config.getInt("duration") * 20, 0));
        }
    }

    @Override
    public void onTrigger(Player p) {
        Location triggererPos = p.getLocation();

        p.getLocation().getNearbyLivingEntities(config.getInt("radius")).forEach(e -> {
            Location locCopy = e.getLocation().clone();

            // Teleport to arena
            e.teleport(worldManager.getSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            // Apply Darkness
            e.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, config.getInt("activeDuration", 10) * 20, 0));

            // Teleport entity back to old location after activeDuration seconds
            rmc.getServer().getScheduler().runTaskLater(rmc, () -> e.teleport(locCopy, PlayerTeleportEvent.TeleportCause.PLUGIN), config.getLong("activeDuration", 10) * 20);
        });

        p.removePotionEffect(PotionEffectType.DARKNESS);

        worldManager.getSpawn().getWorld().playSound(worldManager.getSpawn(),
                Sound.ENTITY_CREAKING_UNFREEZE, 2, 1);

        rmc.getServer().sendMessage(rmc.rt.parse("<black><italic><bold>DOMAIN EXPANSION ABYSSAL VOID"));

        dimensionActive = true;
        rmc.getServer().getScheduler().runTaskLater(rmc, () -> {
            this.dimensionActive = false;
            worldManager.getEntities().forEach(entity -> entity.teleport(triggererPos));
            placedBlocks.forEach(loc -> loc.getBlock().setType(Material.AIR));
            placedBlocks.clear();

        }, config.getLong("activeDuration", 10) * 20 + 1); // one tick later!
    }

    @EventHandler
    void onBlockPlace(BlockPlaceEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        onGeneralBlockPlace(e.getBlock());
    }

    @EventHandler
    void onFallBlock(final EntityChangeBlockEvent e) {
        if (!(e.getEntity() instanceof FallingBlock)) return;

        onGeneralBlockPlace(e.getBlock());
    }

    private void onGeneralBlockPlace(Block block) {
        Location loc = block.getLocation();

        if (!worldManager.isInWorld(loc)) return;

        placedBlocks.add(loc);
    }

    @EventHandler
    void onBucketEmpty(PlayerBucketEmptyEvent e) {
        if (worldManager.isInWorld(e.getBlock().getLocation())) e.setCancelled(true);
    }

    @EventHandler
    void onDimensionChange(EntityPortalEnterEvent e) {
        if (!worldManager.isInWorld(e.getLocation())) return;

        e.setCancelled(true);
    }

    @EventHandler
    void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        Location loc = block.getLocation();

        if (!worldManager.isInWorld(loc)) return;
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;

        if (placedBlocks.remove(loc)) return;

        e.setCancelled(true);
    }

    @EventHandler
    void onEntityExplode(EntityExplodeEvent e) {
        if (worldManager.isInWorld(e.getLocation())) e.setCancelled(true);
    }

    @EventHandler
    void onBlockExplode(BlockExplodeEvent e) {
        if (worldManager.isInWorld(e.getBlock().getLocation())) e.setCancelled(true);
    }


    @EventHandler
    void onPlayerMove(PlayerMoveEvent event) {
        if (!worldManager.isInWorld(event.getPlayer().getLocation())) return;
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        if (dimensionActive) return;
        event.getPlayer().teleport(rmc.getServer().getRespawnWorld().getSpawnLocation());
        event.getPlayer().kick(rmc.rt.parse("<red>VULCAN: UNFAIR ADVANTAGE")); // just for fun :)))))))
    }
}
