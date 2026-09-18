package de.itsgraphax.rmc5.token.potion;

import de.itsgraphax.rmc5.token.Token;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import de.itsgraphax.rmc5.token.TokenRarity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PotionToken extends Token {
    public PotionToken() {
        super(TokenIdentifier.POTION, TokenRarity.RARE);
    }

    @Override
    public void onTrigger(Player p) {
        for (PotionEffect e : p.getActivePotionEffects()) {
            p.addPotionEffect(e.withDuration(
                    e.getDuration() * config.getInt("durationMultiplier")
            ));
        }
    }
}
