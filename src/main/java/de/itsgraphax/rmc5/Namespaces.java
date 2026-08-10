package de.itsgraphax.rmc5;

import de.itsgraphax.grphxLib.utils.NamespacesBase;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import org.bukkit.NamespacedKey;

public class Namespaces extends NamespacesBase {
    public Namespaces(RmcPlugin plugin) {
        super(plugin);
    }

    public NamespacedKey itemToken(TokenIdentifier identifier) {
        return key(identifier.id());
    }
    public NamespacedKey itemNoxiumIngot() {
        return key("noxium_ingot");
    }
    public NamespacedKey itemNoxiumNugget() {
        return key("noxium_nugget");
    }

    public NamespacedKey recipeNoxiomIngotToNugget() {
        return key("noxium_ingot_to_nugget");
    }
    public NamespacedKey recipeNoxiomNuggetToIngot() {
        return key("noxium_nugget_to_ingot");
    }
    public NamespacedKey recipeCreakingToken() {
        return key("creaking_token");
    }

    public NamespacedKey pdcEquippedTokenId(int slot) {
        return key("token." + slot + ".id");
    }
    public NamespacedKey pdcEquippedTokenBroken(int slot) {
        return key("token." + slot + ".broken");
    }

    public NamespacedKey pdcLastTokenUse(TokenIdentifier id) {
        return key("token.cooldown." + id.id());
    }

    public NamespacedKey pdcItemTokenId() {
        return key("token.id");
    }
    public NamespacedKey pdcItemTokenBroken() {
        return key("token.broken");
    }
}
