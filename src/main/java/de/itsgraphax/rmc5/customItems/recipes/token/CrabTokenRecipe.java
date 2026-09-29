package de.itsgraphax.rmc5.customItems.recipes.token;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.grphxLib.citems.CrecipeOverride;
import de.itsgraphax.grphxLib.citems.RequireCitemOverride;
import de.itsgraphax.rmc5.customItems.recipes.RecipeHelper;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.PotionContents;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CrabTokenRecipe implements RecipeHelper {
    public static void register() {
        Citem token = rmc.tokenManager().tokenFromId(TokenIdentifier.CRAB);
        Citem noxiumIngot = ci.get(ns.itemNoxiumIngot());
        assert noxiumIngot != null;

        ShapedRecipe recipe = new ShapedRecipe(ns.recipeCrabToken(), token.createItem());

        recipe.shape(" t ", "pnp", " b ");
        recipe.setIngredient('n', noxiumIngot.createItem().getType());
        recipe.setIngredient('t', Material.TURTLE_SCUTE);
        recipe.setIngredient('p', RecipeChoice.itemType(
                ItemType.ANGLER_POTTERY_SHERD,
                ItemType.SHELTER_POTTERY_SHERD,
                ItemType.SNORT_POTTERY_SHERD,
                ItemType.BLADE_POTTERY_SHERD,
                ItemType.EXPLORER_POTTERY_SHERD,
                ItemType.MOURNER_POTTERY_SHERD,
                ItemType.PLENTY_POTTERY_SHERD
        ));
        recipe.setIngredient('b', Material.POTION);

        ci.override(ns.recipeCrabToken(), new RequireCitemOverride(noxiumIngot, 5));
        ci.override(ns.recipeCrabToken(), new CrecipeOverride((inv) -> {
            ItemStack item = inv.getItem(8);
            assert item != null;

            PotionContents pot = item.getData(DataComponentTypes.POTION_CONTENTS);
            if (pot == null) return false;

            for (PotionEffect eff : pot.allEffects()) {
                if (eff.getType() == PotionEffectType.WATER_BREATHING) return true;
            }
            return false;
        }));

        s.addRecipe(recipe);
    }
}
