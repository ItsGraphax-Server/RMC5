package de.itsgraphax.rmc5.token;

import de.itsgraphax.rmc5.token.air.AirToken;
import de.itsgraphax.rmc5.token.crab.CrabToken;
import de.itsgraphax.rmc5.token.heart.HeartToken;
import de.itsgraphax.rmc5.token.legacyCreaking.LegacyCreakingToken;
import de.itsgraphax.rmc5.token.villager.VillagerToken;
import de.itsgraphax.rmc5.token.warden.WardenToken;
import de.itsgraphax.rmc5.token.fire.FireToken;
import de.itsgraphax.rmc5.token.potion.PotionToken;
import de.itsgraphax.rmc5.token.smelter.SmelterToken;
import de.itsgraphax.rmc5.token.unknown.UnknownToken;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum TokenIdentifier {
    UNKNOWN(UnknownToken::new),
    FIRE(FireToken::new),
    WARDEN(WardenToken::new),
    CREAKING(LegacyCreakingToken::new),
    SMELTER(SmelterToken::new),
    AIR(AirToken::new),
    CRAB(CrabToken::new),
    VILLAGER(VillagerToken::new),
    HEART(HeartToken::new),
    POTION(PotionToken::new);

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
