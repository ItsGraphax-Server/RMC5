package de.itsgraphax.rmc5.customItems.recipes;

import de.itsgraphax.grphxLib.citems.CitemManager;
import de.itsgraphax.rmc5.HasPlugin;
import de.itsgraphax.rmc5.managers.Namespaces;
import org.bukkit.Server;

public interface RecipeHelper extends HasPlugin {
    Namespaces ns = rmc.namespaces();
    CitemManager ci = rmc.cim();
    Server s = rmc.getServer();
}
