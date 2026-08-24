package de.itsgraphax.rmc5.customItems.recipes.citem;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.CrecipeOverride;
import de.itsgraphax.rmc5.customItems.recipes.RecipeHelper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class NoxiumIngotToNugget implements RecipeHelper {
    public static void register() {
        Citem ingot = ci.get(ns.itemNoxiumIngot());
        Citem nugget = ci.get(ns.itemNoxiumNugget());
        assert ingot != null && nugget != null;

        ItemStack nuggetItem = nugget.createItem();
        nuggetItem.setAmount(9);
        ShapedRecipe recipe = new ShapedRecipe(ns.recipeNoxiomIngotToNugget(), nuggetItem);

        recipe.shape("   ", " i ", "   ");
        recipe.setIngredient('i', ingot.createItem().getType());

        ci.override(ns.recipeNoxiomIngotToNugget(), new CrecipeOverride(ingot, 5));

        s.addRecipe(recipe);
    }
}
