package de.itsgraphax.rmc5.customItems.recipes;

public class RecipeManager {
    public static void registerRecipe() {
        NoxiumIngotToNugget.register();
        NoxiumNuggetToIngot.register();

        CreakingToken.register();
    }
}
