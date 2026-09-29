package de.itsgraphax.rmc5;

import de.itsgraphax.grphxLib.citems.CitemListener;
import de.itsgraphax.grphxLib.citems.CitemManager;
import de.itsgraphax.grphxLib.shorthands.OnEnable;
import de.itsgraphax.grphxLib.utils.ResourcepackSender;
import de.itsgraphax.grphxLib.utils.RichText;
import de.itsgraphax.rmc5.commands.DebugBrigadier;
import de.itsgraphax.rmc5.commands.TokenBrigadier;
import de.itsgraphax.rmc5.commands.UnequipBrigadier;
import de.itsgraphax.rmc5.customItems.*;
import de.itsgraphax.rmc5.customItems.listeners.AdvancementListener;
import de.itsgraphax.rmc5.customItems.listeners.RecipeListener;
import de.itsgraphax.rmc5.customItems.listeners.WanderingTraderListener;
import de.itsgraphax.rmc5.customItems.recipes.RecipeManager;
import de.itsgraphax.rmc5.events.EventManager;
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

import java.util.UUID;

public final class RmcPlugin extends JavaPlugin {
    public static RmcPlugin rmc;

    public final CitemManager cim = new CitemManager();
    public final RichText rt = new RichText();

    public final Namespaces ns = new Namespaces(this);
    public final PdcData pdc;
    public final RmcDataManager data;

    public final UIManager uiManager;
    public EventManager eventManager;
    public TokenManager tokenManager;

    public RmcPlugin() {
        super();

        rmc = this;

        pdc = new PdcData();
        data = new RmcDataManager();
        uiManager = new UIManager();
    }

    @Override
    public void onEnable() {
        tokenManager = new TokenManager();
        eventManager = new EventManager();

        cim.register(new NoxiumIngot());
        cim.register(new NoxiumNugget());
        cim.register(new BlueMatter());
        cim.register(new DarkMatter());
        cim.register(new GoldenMatter());
        cim.register(new Repairer());
        RecipeManager.registerRecipe();

        saveDefaultConfig();

        OnEnable.registerEvents(this,
                new CitemListener(cim),
                new TokenListener(),
                new AdvancementListener(),
                new WanderingTraderListener(),
                new RecipeListener(),
                new DisableFireAspectListener(),
                new NoMaceEnchantListener(),
                new Cure4AllListener(),
                new ResourcepackSender("rmc5",
                        UUID.fromString("a4cd4760-324a-4173-b4d8-7c69b19ac03f"))
        );
        OnEnable.registerEvents(this, tokenManager.allTokens());
        OnEnable.registerCommands(this,
                DebugBrigadier::register,
                TokenBrigadier::register,
                UnequipBrigadier::register
        );

        getServer().getScheduler().runTaskTimer(this, () -> {
            tokenManager.tick();
            TraderSpawner.tick();
            uiManager.render();
        }, 1, 1);

        logger().info(rt.parse("RmcPlugin successfully enabled"));
    }

    @Override
    public void onDisable() {
        logger().info(rt.parse("RmcPlugin successfully disabled"));
    }

    @Deprecated
    public static RmcPlugin instance() {
        return rmc;
    }

    @Deprecated
    public ComponentLogger logger() {
        return getComponentLogger();
    }

    @Deprecated
    public CitemManager cim() {
        return cim;
    }

    @Deprecated
    public RichText rt() {
        return rt;
    }

    @Deprecated
    public Namespaces namespaces() {
        return ns;
    }

    @Deprecated
    public PdcData pdc() {
        return pdc;
    }

    @Deprecated
    public RmcDataManager data() {
        return data;
    }

    @Deprecated
    public TokenManager tokenManager() {
        return tokenManager;
    }
}
