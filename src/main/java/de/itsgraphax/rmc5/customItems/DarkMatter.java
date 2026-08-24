package de.itsgraphax.rmc5.customItems;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.rmc5.HasPlugin;

public class DarkMatter extends Citem implements HasPlugin {
    public DarkMatter() {
        super(plugin.namespaces().itemDarkMatter());
    }
}
