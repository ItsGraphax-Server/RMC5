package de.itsgraphax.rmc5.customItems.recipes.citem;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.RequireCitemOverride;
import de.itsgraphax.rmc5.customItems.recipes.RecipeHelper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class DarkRepairerRecipe implements RecipeHelper {
    public static void register() {
        Citem darkMatter = ci.get(ns.itemDarkMatter());
        Citem repairer = ci.get(ns.itemRepairer());
        assert darkMatter != null && repairer != null;

        ItemStack repairerItem = repairer.createItem();
        repairerItem.setAmount(5);
        ShapedRecipe recipe = new ShapedRecipe(ns.recipeDarkRepairer(), repairerItem);

        recipe.shape("iii", "iri", "iii");
        recipe.setIngredient('r', darkMatter.createItem().getType());

        ci.override(ns.recipeDarkRepairer(), new RequireCitemOverride(darkMatter, 5));

        s.addRecipe(recipe);
    }
}
