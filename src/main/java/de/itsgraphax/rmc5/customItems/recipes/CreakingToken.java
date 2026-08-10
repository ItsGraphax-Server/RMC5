package de.itsgraphax.rmc5.customItems.recipes;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.CrecipeOverride;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class CreakingToken implements RecipeHelper {
    public static void register() {
        Citem token = plugin.tokenManager().tokenFromId(TokenIdentifier.CREAKING);
        Citem noxiumIngot = ci.get(ns.itemNoxiumIngot());
        assert noxiumIngot != null;

        ShapedRecipe recipe = new ShapedRecipe(ns.recipeCreakingToken(), token.createItem());

        recipe.shape(" n ", "chd", " l ");
        recipe.setIngredient('n', noxiumIngot.createItem().getType());
        recipe.setIngredient('c', Material.RECOVERY_COMPASS);
        recipe.setIngredient('h', Material.CREAKING_HEART);
        recipe.setIngredient('d', Material.MUSIC_DISC_5);
        recipe.setIngredient('l', Material.PALE_OAK_LOG);

        ci.override(ns.recipeCreakingToken(), new CrecipeOverride(noxiumIngot, 2));

        s.addRecipe(recipe);
    }
}
