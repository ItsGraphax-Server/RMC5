package de.itsgraphax.rmc5.customItems.recipes.token;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.RequireCitemOverride;
import de.itsgraphax.rmc5.customItems.recipes.RecipeHelper;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class AirTokenRecipe implements RecipeHelper {
    public static void register() {
        Citem token = rmc.tokenManager().tokenFromId(TokenIdentifier.AIR);
        Citem noxiumIngot = ci.get(ns.itemNoxiumIngot());
        assert noxiumIngot != null;

        ShapedRecipe recipe = new ShapedRecipe(ns.recipeAirToken(), token.createItem());

        recipe.shape(" c ", "obk", " n ");
        recipe.setIngredient('n', noxiumIngot.createItem().getType());
        recipe.setIngredient('c', Material.HEAVY_CORE);
        recipe.setIngredient('o', Material.OMINOUS_BOTTLE);
        recipe.setIngredient('b', Material.BREEZE_ROD);
        recipe.setIngredient('k', Material.OMINOUS_TRIAL_KEY);

        ci.override(ns.recipeAirToken(), new RequireCitemOverride(noxiumIngot, 8));

        s.addRecipe(recipe);
    }
}
