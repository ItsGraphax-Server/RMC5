package de.itsgraphax.rmc5;

import de.itsgraphax.grphxLib.utils.PdcDataBase;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import io.papermc.paper.persistence.PersistentDataContainerView;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

public class PdcData extends PdcDataBase implements HasPlugin {
    private final @NotNull Namespaces ns = plugin.namespaces();

    public PdcData() {
        super();
    }

    private @NotNull TokenIdentifier getTokenId(@NotNull PersistentDataContainerView pdc, @NotNull NamespacedKey key) {
        String id = pdc.getOrDefault(key, PersistentDataType.STRING, "");
        try {
            return TokenIdentifier.valueOf(id);
        } catch (IllegalArgumentException e) {
            return TokenIdentifier.UNKNOWN;
        }
    }
    private void setTokenId(@NotNull PersistentDataContainer pdc, @NotNull NamespacedKey key, @Nullable TokenIdentifier val) {
        if (val == null) pdc.remove(key);
        else pdc.set(key, PersistentDataType.STRING, val.id());
    }

    private boolean getBroken(@NotNull PersistentDataContainerView pdc, @NotNull NamespacedKey key) {
        return Boolean.TRUE.equals(pdc.get(key, PersistentDataType.BOOLEAN));
    }
    private void setBroken(@NotNull PersistentDataContainer pdc, @NotNull NamespacedKey key, @Nullable Boolean val) {
        if (val == null) pdc.remove(key);
        else pdc.set(key, PersistentDataType.BOOLEAN, val);

    }


    public @NotNull TokenIdentifier getEquippedToken(@NotNull Player p, int slot) {
        return getTokenId(pdc(p), ns.pdcEquippedTokenId(slot));
    }
    public void setEquippedToken(@NotNull Player p, int slot, @Nullable TokenIdentifier val) {
        setTokenId(pdc(p), ns.pdcEquippedTokenId(slot), val);
    }

    public boolean getEquippedBroken(@NotNull Player p, int slot) {
        return getBroken(pdc(p), ns.pdcEquippedTokenBroken(slot));
    }
    public void setEquippedBroken(@NotNull Player p, int slot, @Nullable Boolean val) {
        setBroken(pdc(p), ns.pdcEquippedTokenBroken(slot), val);
    }


    public LocalDateTime getLastUse(@NotNull Player p, TokenIdentifier id) {
        return LocalDateTime.parse(pdc(p)
                .getOrDefault(ns.pdcLastTokenUse(id), PersistentDataType.STRING,
                        LocalDateTime.MIN.toString()));
    }
    public void setLastUse(@NotNull Player p, TokenIdentifier id, LocalDateTime ldt) {
        pdc(p).set(ns.pdcLastTokenUse(id), PersistentDataType.STRING, ldt.toString());
    }
    public void resetLastUse(@NotNull Player p, TokenIdentifier id) {
        pdc(p).remove(ns.pdcLastTokenUse(id));
    }

    public boolean getItemBroken(@NotNull ItemStack item) {
        return getBroken(item.getPersistentDataContainer(), ns.pdcItemTokenBroken());
    }
    public void setItemBroken(@NotNull ItemStack item, @Nullable Boolean val) {
        item.editPersistentDataContainer(pdc -> setBroken(pdc, ns.pdcItemTokenBroken(), val));
    }
}
