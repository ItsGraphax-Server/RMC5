package de.itsgraphax.rmc5.customItems.recipes.citem;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.CrecipeOverride;
import de.itsgraphax.rmc5.customItems.recipes.RecipeHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class Repairer implements RecipeHelper {
    public static void register() {
        Citem repairer = ci.get(ns.itemRepairer());
        Citem blueMatter = ci.get(ns.itemBlueMatter());
        assert blueMatter != null && repairer != null;

        ShapedRecipe recipe = new ShapedRecipe(ns.recipeRepairer(), repairer.createItem());

        recipe.shape("idb", " sd", "s i");
        recipe.setIngredient('b', blueMatter.createItem().getType());
        recipe.setIngredient('i', Material.IRON_BLOCK);
        recipe.setIngredient('d', Material.DIAMOND_BLOCK);
        recipe.setIngredient('s', Material.STICK);

        ci.override(ns.recipeRepairer(), new CrecipeOverride(blueMatter, 3));

        s.addRecipe(recipe);
    }
}
