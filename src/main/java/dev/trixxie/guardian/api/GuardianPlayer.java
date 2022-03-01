package dev.trixxie.guardian.api;

import dev.trixxie.guardian.Guardian;
import dev.trixxie.guardian.events.TagPlayerEvent;
import dev.trixxie.guardian.events.UntagPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GuardianPlayer {

    Guardian plugin;

    public GuardianPlayer(Guardian plugin){
        this.plugin = plugin;
    }

    /**
     * Tag attacker and victim for combat with desired tag duration
     *
     * @param attacker Player who attacked the victim
     * @param victim Player who was attacked by another player
     * @param duration Combat tag duration in seconds
     */
    public void Tag(Player attacker, Player victim, int duration){
            TagPlayerEvent e1 = new TagPlayerEvent(attacker, victim, duration);
            Bukkit.getPluginManager().callEvent(e1);
    }

    /**
     * "Manually" untag player from combat tag, allowing them to safely log out of the server, or perform other actions prevented by the plugin
     *
     * @param player Player to be untagged
     * @param cause Cause for the untag
     *
     */
    public void Untag(Player player, UntagPlayerEvent.UntagCause cause){
        if(plugin.taggedPlayers.containsKey(player.getUniqueId())) {
            UntagPlayerEvent e1 = new UntagPlayerEvent(player, cause);
            Bukkit.getPluginManager().callEvent(e1);
        }
    }

    /**
     * Check if given player is combat tagged
     * @return true if player is tagged
     */
    public boolean TagStatus(Player player){
        return plugin.taggedPlayers.containsKey(player.getUniqueId());
    }
}
