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
    public NamespacedKey itemGoldenMatter() {
        return key("golden_matter");
    }
    public NamespacedKey itemRepairer() {
        return key("repairer");
    }
    public NamespacedKey itemDolphinFin() {
        return key("dolphin_fin");
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
    public NamespacedKey recipeDarkRepairer() {
        return key("darkRepairer");
    }
    public NamespacedKey recipeRepair() {
        return key("repair");
    }

    public NamespacedKey recipeGoldenMatter() {
        return key("golden_matter");
    }

    public NamespacedKey recipeWardenToken() {
        return key("warden_token");
    }
    public NamespacedKey recipeSmelterToken() {
        return key("smelter_token");
    }
    public NamespacedKey recipeAirToken() {
        return key("air_token");
    }
    public NamespacedKey recipeWaterToken() {
        return key("water_token");
    }
    public NamespacedKey recipeFireToken() {
        return key("fire_token");
    }
    public NamespacedKey recipeCrabToken() {
        return key("crab_token");
    }
    public NamespacedKey recipePotionToken() {
        return key("potion_token");
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

    public NamespacedKey pdcBountyCoins() {
        return key("bounty.coins");
    }
    public NamespacedKey pdcBountyBounty() {
        return key("bounty.bounty");
    }
    public NamespacedKey pdcBountyClaims() {
        return key("bounty.claims");
    }
    public NamespacedKey pdcBountyLastUpdateCycle() {
        return key("bounty.lastUpdateCycle");
    }

    public NamespacedKey pdcItemTokenBroken() {
        return key("token.broken");
    }
    public NamespacedKey pdcItemTokenId() {
        return key("token.id");
    }

    public NamespacedKey pdcRareCrafts() {
        return key("rare_rafts");
    }

    public NamespacedKey smelterOverheating() {
        return key("smelter_overheating");
    }

    public NamespacedKey airModifier() {
        return key("air_modifier");
    }

    public NamespacedKey crabModifier() {
        return key("crab_modifier");
    }

    public NamespacedKey heartModifier() {
        return key("heart_modifier");
    }
}
