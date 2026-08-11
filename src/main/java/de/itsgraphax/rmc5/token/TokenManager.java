package de.itsgraphax.rmc5.token;

import de.itsgraphax.rmc5.HasPlugin;
import de.itsgraphax.rmc5.PdcData;
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
            plugin.citemManager().register(instance);
        }
    }

    public void reloadConfig() {
        for (Token token : idTokenMap.values()) {
            token.reloadConfig();
        }
    }
    public void reset(Player p) {
        for (TokenIdentifier id : idTokenMap.keySet()) {
            plugin.pdcData().resetLastUse(p, id);
        }
    }

    public @NotNull Token tokenFromId(@NotNull TokenIdentifier id) {
        return idTokenMap.get(id);
    }
    public @NotNull Collection<Token> allTokens() {
        return idTokenMap.values();
    }

    public void tick() {
        for (Player p : plugin.getServer().getOnlinePlayers()) { // iterate over players
            for (int slot = 0; slot < 2; slot++) { // iterate over slots
                TokenIdentifier id = plugin.pdcData().getEquippedToken(p, slot);
                boolean broken = plugin.pdcData().getEquippedBroken(p, slot);
                if (id == TokenIdentifier.UNKNOWN || broken) continue;
                tokenFromId(id).onTick(p);
            }

            render(p);
        }
    }

    private void render(Player p) {
        Token token0 = tokenFromId(plugin.pdcData().getEquippedToken(p, 0));
        Token token1 = tokenFromId(plugin.pdcData().getEquippedToken(p, 1));
        p.sendActionBar(
                plugin.richText().parse("{{COOLDOWN0}} <sprite:{{ICON0}}> <sprite:{{ICON1}}> {{COOLDOWN1}}",
                        "ICON0", token0.getSprite(p, 0),
                        "ICON1", token1.getSprite(p, 1),
                        "COOLDOWN0", token0.getCooldownString(p),
                        "COOLDOWN1", token1.getCooldownString(p))
        );
    }

    public UnequipResult unequipToken(@NotNull Player p, int slot) {
        PdcData pdcData = plugin.pdcData();

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
