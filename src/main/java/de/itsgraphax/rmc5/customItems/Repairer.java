package de.itsgraphax.rmc5.customItems;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.rmc5.HasPlugin;

public class Repairer extends Citem implements HasPlugin {
    public Repairer() {
        super(plugin.namespaces().itemRepairer());
    }
}
