package de.itsgraphax.rmc5;

import de.itsgraphax.grphxLib.citems.CitemListener;
import de.itsgraphax.grphxLib.citems.CitemManager;
import de.itsgraphax.grphxLib.shorthands.OnEnable;
import de.itsgraphax.grphxLib.utils.RichText;
import de.itsgraphax.rmc5.commands.DebugBrigadier;
import de.itsgraphax.rmc5.commands.TokenBrigadier;
import de.itsgraphax.rmc5.commands.UnequipBrigadier;
import de.itsgraphax.rmc5.customItems.*;
import de.itsgraphax.rmc5.customItems.listeners.AdvancementListener;
import de.itsgraphax.rmc5.customItems.listeners.RecipeListener;
import de.itsgraphax.rmc5.customItems.listeners.WanderingTraderListener;
import de.itsgraphax.rmc5.customItems.recipes.RecipeManager;
import de.itsgraphax.rmc5.managers.Namespaces;
import de.itsgraphax.rmc5.managers.PdcData;
import de.itsgraphax.rmc5.managers.RmcDataManager;
import de.itsgraphax.rmc5.managers.UIManager;
import de.itsgraphax.rmc5.misc.Cure4AllListener;
import de.itsgraphax.rmc5.misc.DisableFireAspectListener;
import de.itsgraphax.rmc5.misc.NoMaceEnchantListener;
import de.itsgraphax.rmc5.misc.TraderSpawner;
import de.itsgraphax.rmc5.token.TokenListener;
import de.itsgraphax.rmc5.token.TokenManager;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class RmcPlugin extends JavaPlugin {
    private static RmcPlugin instance;

    private final CitemManager citemManager = new CitemManager();
    private final RichText richText = new RichText();

    private final Namespaces namespaces = new Namespaces(this);
    private final PdcData pdcData;
    private final RmcDataManager dataManager;

    private final UIManager uiManager;
    private TokenManager tokenManager;

    public RmcPlugin() {
        super();

        instance = this;

        pdcData = new PdcData();
        dataManager = new RmcDataManager();
        uiManager = new UIManager();
    }

    @Override
    public void onEnable() {
        tokenManager = new TokenManager();
        citemManager.register(new NoxiumIngot());
        citemManager.register(new NoxiumNugget());
        citemManager.register(new BlueMatter());
        citemManager.register(new DarkMatter());
        citemManager.register(new GoldenMatter());
        citemManager.register(new Repairer());
        RecipeManager.registerRecipe();

        saveDefaultConfig();

        OnEnable.registerEvents(Set.of(
                new CitemListener(citemManager),
                new TokenListener(),
                new AdvancementListener(),
                new WanderingTraderListener(),
                new RecipeListener(),
                new DisableFireAspectListener(),
                new NoMaceEnchantListener(),
                new Cure4AllListener()
        ), this);
        OnEnable.registerEvents(tokenManager.allTokens(), this);
        OnEnable.registerCommands(Set.of(
                DebugBrigadier::register,
                TokenBrigadier::register,
                UnequipBrigadier::register
        ), this);

        getServer().getScheduler().runTaskTimer(this, () -> {
            tokenManager.tick();
            TraderSpawner.tick();
            uiManager.render();
        }, 1, 1);

        logger().info(richText.parse("RmcPlugin successfully enabled"));
    }

    @Override
    public void onDisable() {
        logger().info(richText.parse("RmcPlugin successfully disabled"));
    }

    public static RmcPlugin instance() {
        return instance;
    }

    public ComponentLogger logger() {
        return getComponentLogger();
    }

    public CitemManager citemManager() {
        return citemManager;
    }

    public RichText richText() {
        return richText;
    }

    public Namespaces namespaces() {
        return namespaces;
    }

    public PdcData getPdcData() {
        return pdcData;
    }

    public RmcDataManager getDataManager() {
        return dataManager;
    }

    public TokenManager tokenManager() {
        return tokenManager;
    }
}
