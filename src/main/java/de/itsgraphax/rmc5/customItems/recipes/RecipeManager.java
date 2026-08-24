package de.itsgraphax.rmc5.customItems.recipes;

import de.itsgraphax.rmc5.customItems.recipes.citem.NoxiumIngotToNugget;
import de.itsgraphax.rmc5.customItems.recipes.citem.NoxiumNuggetToIngot;
import de.itsgraphax.rmc5.customItems.recipes.citem.Repair;
import de.itsgraphax.rmc5.customItems.recipes.citem.Repairer;
import de.itsgraphax.rmc5.customItems.recipes.token.AirToken;
import de.itsgraphax.rmc5.customItems.recipes.token.CreakingToken;
import de.itsgraphax.rmc5.customItems.recipes.token.SmelterToken;

public class RecipeManager {
    public static void registerRecipe() {
        NoxiumIngotToNugget.register();
        NoxiumNuggetToIngot.register();
        Repairer.register();
        Repair.register();

        CreakingToken.register();
        SmelterToken.register();
        AirToken.register();
    }
}
