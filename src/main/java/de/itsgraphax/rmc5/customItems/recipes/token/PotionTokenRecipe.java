package de.itsgraphax.rmc5.customItems.recipes.token;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.RequireCitemOverride;
import de.itsgraphax.rmc5.customItems.recipes.RecipeHelper;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class PotionTokenRecipe implements RecipeHelper {
    public static void register() {
        Citem token = rmc.tokenManager().tokenFromId(TokenIdentifier.POTION);
        Citem noxiumIngot = ci.get(ns.itemNoxiumIngot());
        assert noxiumIngot != null;

        ShapedRecipe recipe = new ShapedRecipe(ns.recipePotionToken(), token.createItem());

        recipe.shape(" s ", "ana", " u ");
        recipe.setIngredient('n', noxiumIngot.createItem().getType());
        recipe.setIngredient('s', Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE);
        recipe.setIngredient('u', Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
        recipe.setIngredient('a', Material.ANCIENT_DEBRIS);

        ci.override(ns.recipePotionToken(), new RequireCitemOverride(noxiumIngot, 5));

        s.addRecipe(recipe);
    }
}
