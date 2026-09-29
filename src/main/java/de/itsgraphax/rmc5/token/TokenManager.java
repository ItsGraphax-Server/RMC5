package de.itsgraphax.rmc5.token;

import de.itsgraphax.rmc5.HasPlugin;
import de.itsgraphax.rmc5.managers.PdcData;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TokenManager implements HasPlugin {
    private final Map<TokenIdentifier, Token> idTokenMap = new HashMap<>();

    public TokenManager() {
        for (TokenIdentifier id : TokenIdentifier.values()) {
            Token instance = id.factory().get();
            idTokenMap.put(id, instance);
            rmc.cim().register(instance);
        }
    }

    public void reloadConfig() {
        for (Token token : idTokenMap.values()) {
            token.reloadConfig();
        }
    }

    public void reset(Player p) {
        for (TokenIdentifier id : idTokenMap.keySet()) {
            rmc.pdc().resetLastUse(p, id);
        }
    }

    public @NotNull Token tokenFromId(@NotNull TokenIdentifier id) {
        return idTokenMap.get(id);
    }

    public @NotNull Token tokenFromItem(@NotNull ItemStack item) {
        return tokenFromId(rmc.pdc().getItemToken(item));
    }

    public @NotNull Collection<Token> allTokens() {
        return idTokenMap.values();
    }

    public void tick() {
        for (Player p : rmc.getServer().getOnlinePlayers()) { // iterate over players
            for (int slot = 0; slot < 2; slot++) { // iterate over slots
                TokenIdentifier id = rmc.pdc().getEquippedToken(p, slot);
                boolean broken = rmc.pdc().getEquippedBroken(p, slot);
                if (id == TokenIdentifier.UNKNOWN || broken) continue;
                tokenFromId(id).onTick(p);
            }
        }
    }

    public Component renderTokenUi(Player p) {
        Token token0 = tokenFromId(rmc.pdc().getEquippedToken(p, 0));
        Token token1 = tokenFromId(rmc.pdc().getEquippedToken(p, 1));
        return
                rmc.rt().parse("{{COOLDOWN0}} <sprite:{{ICON0}}> <sprite:{{ICON1}}> {{COOLDOWN1}}",
                        "ICON0", token0.getSprite(p, 0),
                        "ICON1", token1.getSprite(p, 1),
                        "COOLDOWN0", token0.getCooldownString(p),
                        "COOLDOWN1", token1.getCooldownString(p));
    }

    public UnequipResult unequipToken(@NotNull Player p, int slot) {
        PdcData pdcData = rmc.pdc();

        TokenIdentifier id = pdcData.getEquippedToken(p, slot);
        if (id == TokenIdentifier.UNKNOWN) return UnequipResult.NO_TOKEN;
        boolean broken = pdcData.getEquippedBroken(p, slot);
        pdcData.setEquippedToken(p, slot, null);
        pdcData.setEquippedBroken(p, slot, null);

        Token token = tokenFromId(id);
        ItemStack item = token.createItem(broken);

        if (!broken) token.onUnequip(p);

        p.give(item);

        return UnequipResult.UNEQUIPPED;
    }

    public enum UnequipResult {
        UNEQUIPPED,
        NO_TOKEN
    }
}
