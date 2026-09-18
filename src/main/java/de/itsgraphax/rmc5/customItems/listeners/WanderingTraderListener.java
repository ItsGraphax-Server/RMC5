package de.itsgraphax.rmc5.customItems.listeners;

import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.rmc5.HasPlugin;
import org.bukkit.Material;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class WanderingTraderListener implements Listener, HasPlugin {
    @EventHandler
    void onSpawn(EntitySpawnEvent e) {
        if (!(e.getEntity() instanceof WanderingTrader entity)) return;

        List<MerchantRecipe> trades = new ArrayList<>(entity.getRecipes());

        Citem citem;
        if (Math.random() < 0.05) citem = plugin.citemManager().get(plugin.namespaces().itemDarkMatter());
        else citem = plugin.citemManager().get(plugin.namespaces().itemBlueMatter());
        assert citem != null;

        ItemStack result = citem.createItem();
        MerchantRecipe trade = new MerchantRecipe(result, 1);
        trade.addIngredient(ItemStack.of(Material.EMERALD_ORE));

        trades.addFirst(trade);

        entity.setRecipes(trades);
    }
}
