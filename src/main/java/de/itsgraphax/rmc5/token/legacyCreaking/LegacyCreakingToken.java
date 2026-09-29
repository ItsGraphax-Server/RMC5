package de.itsgraphax.rmc5.token.legacyCreaking;

import de.itsgraphax.rmc5.token.Token;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import de.itsgraphax.rmc5.token.TokenRarity;
import org.bukkit.entity.Player;

public class LegacyCreakingToken extends Token {
    public LegacyCreakingToken() {
        super(TokenIdentifier.CREAKING, TokenRarity.EPIC);
    }

    @Override
    public void onTick(Player p) {
        rmc.pdc().setEquippedToken(p, getSlot(), TokenIdentifier.WARDEN);
    }
}
