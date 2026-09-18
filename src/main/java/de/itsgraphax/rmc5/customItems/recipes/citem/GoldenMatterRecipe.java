package de.itsgraphax.rmc5.customItems.recipes.citem;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.RequireCitemOverride;
import de.itsgraphax.rmc5.customItems.recipes.RecipeHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class GoldenMatterRecipe implements RecipeHelper {
    public static void register() {
        Citem darkMatter = ci.get(ns.itemDarkMatter());
        Citem goldenMatter = ci.get(ns.itemGoldenMatter());
        assert darkMatter != null && goldenMatter != null;

        ShapedRecipe recipe = new ShapedRecipe(ns.recipeGoldenMatter(), goldenMatter.createItem());

        recipe.shape("bbb", "gdg", "bbb");
        recipe.setIngredient('d', darkMatter.createItem().getType());
        recipe.setIngredient('g', Material.GOLD_BLOCK);
        recipe.setIngredient('b', Material.BLAZE_POWDER);

        ci.override(ns.recipeGoldenMatter(), new RequireCitemOverride(darkMatter, 5));

        s.addRecipe(recipe);
    }
}
