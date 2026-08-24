package de.itsgraphax.rmc5.managers;

import de.itsgraphax.grphxLib.utils.NamespacesBase;
import de.itsgraphax.rmc5.RmcPlugin;
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
    public NamespacedKey itemBlueMatter() {
        return key("blue_matter");
    }
    public NamespacedKey itemDarkMatter() {
        return key("dark_matter");
    }
    public NamespacedKey itemRepairer() {
        return key("repairer");
    }

    public NamespacedKey recipeNoxiomIngotToNugget() {
        return key("noxium_ingot_to_nugget");
    }
    public NamespacedKey recipeNoxiomNuggetToIngot() {
        return key("noxium_nugget_to_ingot");
    }

    public NamespacedKey recipeRepairer() {
        return key("repairer");
    }
    public NamespacedKey recipeRepair() {
        return key("repair");
    }

    public NamespacedKey recipeCreakingToken() {
        return key("creaking_token");
    }
    public NamespacedKey recipeSmelterToken() {
        return key("smelter_token");
    }
    public NamespacedKey recipeAirToken() {
        return key("air_token");
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

    public NamespacedKey pdcItemTokenBroken() {
        return key("token.broken");
    }
    public NamespacedKey pdcItemTokenId() {
        return key("token.id");
    }

    public NamespacedKey pdcRareCrafts() {
        return key("rareCrafts");
    }

    public NamespacedKey smelterOverheating() {
        return key("smelter_overheating");
    }

    public NamespacedKey airModifier() {
        return key("air_modifier");
    }
}
