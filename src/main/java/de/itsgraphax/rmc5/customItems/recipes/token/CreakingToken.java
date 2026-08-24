package de.itsgraphax.rmc5.customItems.recipes.token;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.CrecipeOverride;
import de.itsgraphax.rmc5.customItems.recipes.RecipeHelper;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class CreakingToken implements RecipeHelper {
    public static void register() {
        Citem token = plugin.tokenManager().tokenFromId(TokenIdentifier.CREAKING);
        Citem noxiumIngot = ci.get(ns.itemNoxiumIngot());
        assert noxiumIngot != null;

        ShapedRecipe recipe = new ShapedRecipe(ns.recipeCreakingToken(), token.createItem());

        recipe.shape(" c ", "rnd", " s ");
        recipe.setIngredient('n', noxiumIngot.createItem().getType());
        recipe.setIngredient('c', Material.CALIBRATED_SCULK_SENSOR);
        recipe.setIngredient('r', Material.RECOVERY_COMPASS);
        recipe.setIngredient('d', Material.MUSIC_DISC_5);
        recipe.setIngredient('s', Material.SCULK_CATALYST);

        ci.override(ns.recipeCreakingToken(), new CrecipeOverride(noxiumIngot, 5));

        s.addRecipe(recipe);
    }
}
