package de.itsgraphax.rmc5.customItems.recipes.citem;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.RequireCitemOverride;
import de.itsgraphax.rmc5.customItems.recipes.RecipeHelper;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class RepairRecipe implements RecipeHelper {
    public static void register() {
        Citem repairer = ci.get(ns.itemRepairer());
        Citem token = plugin.tokenManager().tokenFromId(TokenIdentifier.UNKNOWN);
        assert repairer != null;

        ShapedRecipe recipe = new ShapedRecipe(ns.recipeRepair(), ItemStack.of(Material.BARRIER));

        recipe.shape("tr ", "   ", "   ");
        recipe.setIngredient('t', token.createItem().getType());
        recipe.setIngredient('r', repairer.createItem().getType());

        ci.override(ns.recipeRepair(), new RequireCitemOverride(repairer, 2));

        s.addRecipe(recipe);
    }
}
