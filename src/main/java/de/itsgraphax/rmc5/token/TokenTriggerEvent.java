package de.itsgraphax.rmc5.token;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TokenTriggerEvent extends TokenEvent {
    public TokenTriggerEvent(@NotNull Player player) {
        super(player);
    }
}
