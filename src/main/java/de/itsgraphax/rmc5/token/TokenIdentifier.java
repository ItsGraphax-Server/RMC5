package de.itsgraphax.rmc5.token;

import de.itsgraphax.rmc5.token.creaking.CreakingToken;
import de.itsgraphax.rmc5.token.fire.FireToken;
import de.itsgraphax.rmc5.token.smelter.SmelterToken;
import de.itsgraphax.rmc5.token.unknown.UnknownToken;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum TokenIdentifier {
    UNKNOWN(UnknownToken::new),
    FIRE(FireToken::new),
    CREAKING(CreakingToken::new),
    SMELTER(SmelterToken::new);

    private final Supplier<Token> factory;

    TokenIdentifier(Supplier<Token> factory) {
        this.factory = factory;
    }

    public String id() {
        return toString();
    }
    public Supplier<Token> factory() {
        return factory;
    }

    public static Set<String> allStrings() {
        return Arrays.stream(values())
                .map(TokenIdentifier::name)
                .collect(Collectors.toSet());
    }
}
