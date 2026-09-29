package de.itsgraphax.rmc5.customItems.recipes.token;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.RequireCitemOverride;
import de.itsgraphax.rmc5.customItems.recipes.RecipeHelper;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class FireTokenRecipe implements RecipeHelper {
    public static void register() {
        Citem token = rmc.tokenManager().tokenFromId(TokenIdentifier.FIRE);
        Citem noxiumIngot = ci.get(ns.itemNoxiumIngot());
        assert noxiumIngot != null;

        ShapedRecipe recipe = new ShapedRecipe(ns.recipeFireToken(), token.createItem());

        recipe.shape("ggg", "gng", "ooo");
        recipe.setIngredient('n', noxiumIngot.createItem().getType());
        recipe.setIngredient('g', Material.GLOWSTONE);
        recipe.setIngredient('o', Material.CRYING_OBSIDIAN);

        ci.override(ns.recipeFireToken(), new RequireCitemOverride(noxiumIngot, 5));

        s.addRecipe(recipe);
    }
}
