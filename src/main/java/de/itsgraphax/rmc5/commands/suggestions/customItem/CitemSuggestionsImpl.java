package de.itsgraphax.rmc5.commands.suggestions.customItem;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.itsgraphax.grphxLib.citems.Citem;
import de.itsgraphax.rmc5.HasPlugin;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class CitemSuggestionsImpl implements HasPlugin {
    private static final Collection<Citem> SUGGESTIONS = rmc.cim().values();

    @CitemSuggestions
    public static CompletableFuture<Suggestions> provide(CommandContext<CommandSourceStack> ctx, SuggestionsBuilder builder) {
        SUGGESTIONS.stream()
                .filter(citem -> citem.key().toString().toLowerCase().startsWith(builder.getRemainingLowerCase()))
                .forEach(citem -> builder.suggest(citem.key().toString()));
        return builder.buildFuture();
    }
}