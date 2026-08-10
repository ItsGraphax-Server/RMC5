package de.itsgraphax.rmc5.customItems.recipes;

import de.itsgraphax.grphxLib.citems.CitemManager;
import de.itsgraphax.rmc5.HasPlugin;
import de.itsgraphax.rmc5.Namespaces;
import org.bukkit.Server;

public interface RecipeHelper extends HasPlugin {
    Namespaces ns = plugin.namespaces();
    CitemManager ci = plugin.citemManager();
    Server s = plugin.getServer();
}
