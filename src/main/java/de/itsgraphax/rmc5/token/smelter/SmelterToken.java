package de.itsgraphax.rmc5.token.smelter;

import de.itsgraphax.rmc5.misc.Utils;
import de.itsgraphax.rmc5.token.Token;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import de.itsgraphax.rmc5.token.TokenRarity;
import io.papermc.paper.block.TileStateInventoryHolder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

import static de.itsgraphax.rmc5.RmcPlugin.rmc;

public class SmelterToken extends Token {
    protected static final PotionEffect strength = new PotionEffect(PotionEffectType.STRENGTH, 5, 2);

    public SmelterToken() {
        super(TokenIdentifier.SMELTER, TokenRarity.RARE);
    }


    private @Nullable ItemStack getFirstApplicableRecipeResult(ItemStack item) {
        ItemStack result = null;

        for (@NotNull Iterator<Recipe> it = rmc.getServer().recipeIterator(); it.hasNext(); ) {
            Recipe unspecificRecipe = it.next();
            if (!(unspecificRecipe instanceof FurnaceRecipe recipe)) continue;

            if (recipe.getInputChoice().test(item)) {
                result = recipe.getResult();
                break;
            }
        }

        if (result == null) return null;

        result.setAmount(item.getAmount());

        return result;
    }

    private void drop(ItemStack item, Location loc) {
        if (item == null) return;
        Location clone = loc.clone();
        loc.getWorld().dropItemNaturally(clone.add(0.5, 0.5, 0.5), item);
    }

    private void particles(Location loc) {
        loc.getWorld().spawnParticle(Particle.FLAME, loc, 15, 0.1, 0.1, 0.1);
    }

    private boolean shouldTrigger(Player p) {
        ItemStack activeItem = p.getInventory().getItemInMainHand();
        return config.getStringList("appliesTo").contains(activeItem.getType().name()) &&
                activeItem.getEnchantmentLevel(Enchantment.SILK_TOUCH) == 0;
    }

    @EventHandler
    void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!hasWorkingToken(p) || !shouldTrigger(p)) return;
        Block block = e.getBlock();
        Location blockLoc = block.getLocation();

        if (block.getState() instanceof TileStateInventoryHolder && block.getType() != Material.FURNACE) return;

        e.setDropItems(false);

        if (e.getExpToDrop() > 0) {
            ExperienceOrb orb = block.getWorld().spawn(blockLoc, ExperienceOrb.class);
            orb.setExperience(e.getExpToDrop());
        }

        if (block.getType() != Material.FURNACE) {
            for (ItemStack item : block.getDrops(p.getInventory().getItemInMainHand(), p)) {
                ItemStack result = getFirstApplicableRecipeResult(item);
                if (result == null) drop(item, blockLoc);
                else {
                    drop(result, blockLoc);
                    particles(blockLoc);
                }
            }
        } else {
            if (!(block.getState() instanceof Furnace furnace)) throw new RuntimeException();
            FurnaceInventory inv = furnace.getInventory();
            drop(ItemStack.of(Material.FURNACE), blockLoc);
            drop(inv.getFuel(), blockLoc);
            drop(inv.getResult(), blockLoc);

            ItemStack result = getFirstApplicableRecipeResult(inv.getItem(0));
            if (result != null) {
                drop(result, blockLoc);
                particles(blockLoc);
            }
        }
    }

    @EventHandler
    void onEntityDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        if (entity.getType() == EntityType.PLAYER) return;
        if (!(e.getDamageSource().getCausingEntity() instanceof Player p)) return;

        if (!hasWorkingToken(p) || !shouldTrigger(p)) return;

        List<ItemStack> drops = e.getDrops();
        for (int i = 0; i < drops.size(); i++) {
            ItemStack drop = drops.get(i);
            ItemStack result = getFirstApplicableRecipeResult(drop);
            if (result != null) {
                drops.set(i, result);
                particles(entity.getLocation());
            }
        }
    }


    @Override
    public void onActiveTick(Player p) {
        p.setFoodLevel(6);
        p.addPotionEffect(strength);
        if (rmc.getServer().getCurrentTick() % 5 == 0) {
            Utils.circleParticles(p.getLocation(), 1, 0.5f, Particle.DUST, new Particle.DustOptions(Color.ORANGE, 1));
        }
    }

    @Override
    public void onTrigger(Player p) {
        p.getWorld().playSound(
                p.getLocation(),
                Sound.ENTITY_GHAST_SCREAM,
                1, 1
        );
        particles(p.getLocation());
    }
}
