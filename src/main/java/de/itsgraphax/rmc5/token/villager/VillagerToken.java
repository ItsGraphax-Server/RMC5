package de.itsgraphax.rmc5.token.villager;

import de.itsgraphax.rmc5.token.Token;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import de.itsgraphax.rmc5.token.TokenRarity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VillagerToken extends Token {
    static final PotionEffect heroOfTheVillage = new PotionEffect(
            PotionEffectType.HERO_OF_THE_VILLAGE, 20, 4, true);

    public VillagerToken() {
        super(TokenIdentifier.VILLAGER, TokenRarity.LEGENDARY);
    }

    @Override
    public void onTick(Player p) {
        p.addPotionEffect(heroOfTheVillage);
    }
}
