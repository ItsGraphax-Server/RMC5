package de.itsgraphax.rmc5.token.unknown;

import de.itsgraphax.rmc5.token.Token;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import de.itsgraphax.rmc5.token.TokenRarity;
import org.bukkit.entity.Player;

public class UnknownToken extends Token {
    public UnknownToken() {
        super(TokenIdentifier.UNKNOWN, TokenRarity.RARE);
    }

    @Override
    public String getSprite(Player p, int slot) {
        return "items:item/barrier";
    }
}
