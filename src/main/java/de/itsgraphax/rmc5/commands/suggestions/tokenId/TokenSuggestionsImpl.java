package de.itsgraphax.rmc5.commands.suggestions.tokenId;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.itsgraphax.rmc5.token.TokenIdentifier;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TokenSuggestionsImpl {
    private static final Set<String> SUGGESTIONS = TokenIdentifier.allStrings();

    @TokenSuggestions
    public static CompletableFuture<Suggestions> provide(CommandContext<CommandSourceStack> ctx, SuggestionsBuilder builder) {
        SUGGESTIONS.stream()
                .filter(str -> str.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                .forEach(builder::suggest);
        return builder.buildFuture();
    }
}