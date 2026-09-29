package de.itsgraphax.rmc5.customItems.listeners;

import de.itsgraphax.rmc5.HasPlugin;
import de.itsgraphax.rmc5.token.Token;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;

public class RecipeListener implements Listener, HasPlugin {
    @EventHandler
    void onPrepCraft(PrepareItemCraftEvent e) {
        if (!(e.getRecipe() instanceof CraftingRecipe recipe)) return;
        if (!recipe.getKey().equals(rmc.namespaces().recipeRepair())) return;

        CraftingInventory inv = e.getInventory();
        ItemStack tokenItem = inv.getItem(1);
        assert tokenItem != null;

        if (!rmc.pdc().getItemBroken(tokenItem)) inv.setResult(null);
        else {
            Token token = rmc.tokenManager().tokenFromItem(tokenItem);
            inv.setResult(token.createItem(false));
        }
    }
}
