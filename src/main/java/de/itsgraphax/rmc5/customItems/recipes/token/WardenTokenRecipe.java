package de.itsgraphax.rmc5.customItems.recipes.token;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.RequireCitemOverride;
import de.itsgraphax.rmc5.customItems.recipes.RecipeHelper;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class WardenTokenRecipe implements RecipeHelper {
    public static void register() {
        Citem token = plugin.tokenManager().tokenFromId(TokenIdentifier.WARDEN);
        Citem noxiumIngot = ci.get(ns.itemNoxiumIngot());
        assert noxiumIngot != null;

        ShapedRecipe recipe = new ShapedRecipe(ns.recipeWardenToken(), token.createItem());

        recipe.shape(" c ", "rnd", " s ");
        recipe.setIngredient('n', noxiumIngot.createItem().getType());
        recipe.setIngredient('c', Material.CALIBRATED_SCULK_SENSOR);
        recipe.setIngredient('r', Material.RECOVERY_COMPASS);
        recipe.setIngredient('d', Material.MUSIC_DISC_5);
        recipe.setIngredient('s', Material.SCULK_CATALYST);

        ci.override(ns.recipeWardenToken(), new RequireCitemOverride(noxiumIngot, 5));

        s.addRecipe(recipe);
    }
}
