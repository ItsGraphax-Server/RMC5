package de.itsgraphax.rmc5.customItems.recipes.token;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.RequireCitemOverride;
import de.itsgraphax.rmc5.customItems.recipes.RecipeHelper;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class WaterTokenRecipe implements RecipeHelper {
    public static void register() {
        Citem token = rmc.tokenManager.tokenFromId(TokenIdentifier.WATER);
        Citem noxiumIngot = ci.get(ns.itemNoxiumIngot());
        Citem dolphinFin = ci.get(ns.itemDolphinFin());
        assert noxiumIngot != null && dolphinFin != null;

        ShapedRecipe recipe = new ShapedRecipe(ns.recipeWaterToken(), token.createItem());

        recipe.shape(" d ", "dnd", " g ");
        recipe.setIngredient('n', noxiumIngot.createItem().getType());
        recipe.setIngredient('d', dolphinFin.createItem().getType());
        recipe.setIngredient('g', Material.GOLDEN_DANDELION);

        ci.override(ns.recipeWaterToken(), new RequireCitemOverride(noxiumIngot, 5));
        ci.override(ns.recipeWaterToken(), new RequireCitemOverride(dolphinFin, 2));
        ci.override(ns.recipeWaterToken(), new RequireCitemOverride(dolphinFin, 4));
        ci.override(ns.recipeWaterToken(), new RequireCitemOverride(dolphinFin, 6));

        s.addRecipe(recipe);
    }
}
