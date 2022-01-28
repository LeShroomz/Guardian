package dev.trixxie.guardian.placeholders;

import dev.trixxie.guardian.Guardian;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class TagPlaceholder extends PlaceholderExpansion {

    Guardian plugin;
    public TagPlaceholder(Guardian plugin){
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "guardian";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Trixxie";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        if(params.equals("tagStatus")){
            String status;
            if(plugin.taggedPlayers.containsKey(p.getUniqueId())){
                status = "TAGGED";
            } else {
                status = "NOT TAGGED";
            }
            return status;
        }

        return null;
    }
}
