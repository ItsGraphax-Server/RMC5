package de.itsgraphax.rmc5;

import de.itsgraphax.grphxLib.citems.CitemListener;
import de.itsgraphax.grphxLib.citems.CitemManager;
import de.itsgraphax.grphxLib.shorthands.OnEnable;
import de.itsgraphax.grphxLib.utils.RichText;
import de.itsgraphax.rmc5.commands.DebugBrigadier;
import de.itsgraphax.rmc5.commands.TokenBrigadier;
import de.itsgraphax.rmc5.commands.UnequipBrigadier;
import de.itsgraphax.rmc5.customItems.NoxiumIngot;
import de.itsgraphax.rmc5.customItems.AdvancementListener;
import de.itsgraphax.rmc5.customItems.NoxiumNugget;
import de.itsgraphax.rmc5.customItems.recipes.RecipeManager;
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

    private TokenManager tokenManager;

    public RmcPlugin() {
        super();

        instance = this;

        pdcData = new PdcData();
    }

    @Override
    public void onEnable() {
        tokenManager = new TokenManager();
        citemManager.register(new NoxiumIngot());
        citemManager.register(new NoxiumNugget());
        RecipeManager.registerRecipe();

        saveDefaultConfig();

        OnEnable.registerEvents(Set.of(
                new CitemListener(citemManager),
                new TokenListener(),
                new AdvancementListener()
        ), this);
        OnEnable.registerEvents(tokenManager.allTokens(), this);
        OnEnable.registerCommands(Set.of(
                DebugBrigadier::register,
                TokenBrigadier::register,
                UnequipBrigadier::register
        ), this);

        getServer().getScheduler().runTaskTimer(this, tokenManager::tick, 1, 1);

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
    public PdcData pdcData() {
        return pdcData;
    }

    public TokenManager tokenManager() {
        return tokenManager;
    }
}
