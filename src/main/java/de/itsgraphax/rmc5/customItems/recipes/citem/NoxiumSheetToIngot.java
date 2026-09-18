package de.itsgraphax.rmc5.customItems.recipes.citem;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.RequireCitemOverride;
import de.itsgraphax.rmc5.customItems.recipes.RecipeHelper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class NoxiumSheetToIngot implements RecipeHelper {
    public static void register() {
        Citem ingot = ci.get(ns.itemNoxiumIngot());
        Citem nugget = ci.get(ns.itemNoxiumNugget());
        assert ingot != null && nugget != null;

        ItemStack ingotItem = ingot.createItem();
        ingotItem.setAmount(1);
        ShapedRecipe recipe = new ShapedRecipe(ns.recipeNoxiomNuggetToIngot(), ingotItem);

        recipe.shape("nnn", "nnn", "nnn");
        recipe.setIngredient('n', nugget.createItem().getType());

        ci.override(ns.recipeNoxiomNuggetToIngot(), new RequireCitemOverride(nugget, 5));

        s.addRecipe(recipe);
    }
}
