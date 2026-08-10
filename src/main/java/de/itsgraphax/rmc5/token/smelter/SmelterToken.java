package de.itsgraphax.rmc5.token.smelter;

import de.itsgraphax.rmc5.token.Token;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import de.itsgraphax.rmc5.token.TokenRarity;

public class SmelterToken extends Token {
    public SmelterToken() {
        super(TokenIdentifier.SMELTER, TokenRarity.RARE);
    }
}
