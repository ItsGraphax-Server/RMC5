package de.itsgraphax.rmc5.customItems.recipes;

import de.itsgraphax.rmc5.customItems.recipes.citem.*;
import de.itsgraphax.rmc5.customItems.recipes.token.*;

public class RecipeManager {
    public static void registerRecipe() {
        NoxiumIngotToNugget.register();
        NoxiumSheetToIngot.register();
        RepairerRecipe.register();
        DarkRepairerRecipe.register();
        RepairRecipe.register();
        GoldenMatterRecipe.register();

        WardenTokenRecipe.register();
        SmelterTokenRecipe.register();
        AirTokenRecipe.register();
        FireTokenRecipe.register();
        CrabTokenRecipe.register();
    }
}
