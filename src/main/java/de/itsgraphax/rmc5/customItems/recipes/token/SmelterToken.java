package de.itsgraphax.rmc5.customItems.recipes.token;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.CrecipeOverride;
import de.itsgraphax.rmc5.customItems.recipes.RecipeHelper;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class SmelterToken implements RecipeHelper {
    public static void register() {
        Citem token = plugin.tokenManager().tokenFromId(TokenIdentifier.SMELTER);
        Citem noxiumIngot = ci.get(ns.itemNoxiumIngot());
        assert noxiumIngot != null;

        ShapedRecipe recipe = new ShapedRecipe(ns.recipeSmelterToken(), token.createItem());

        recipe.shape(" g ", "ine", " d ");
        recipe.setIngredient('n', noxiumIngot.createItem().getType());
        recipe.setIngredient('i', Material.IRON_BLOCK);
        recipe.setIngredient('g', Material.GOLD_BLOCK);
        recipe.setIngredient('e', Material.EMERALD_BLOCK);
        recipe.setIngredient('d', Material.DIAMOND_BLOCK);

        ci.override(ns.recipeSmelterToken(), new CrecipeOverride(noxiumIngot, 5));

        s.addRecipe(recipe);
    }
}
