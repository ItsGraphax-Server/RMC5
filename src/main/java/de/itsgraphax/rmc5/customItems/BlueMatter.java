package de.itsgraphax.rmc5.customItems;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.rmc5.HasPlugin;

public class BlueMatter extends Citem implements HasPlugin {
    public BlueMatter() {
        super(rmc.namespaces().itemBlueMatter());
    }
}
