package dev.trixxie.guardian.listeners;

import dev.trixxie.guardian.Guardian;
import dev.trixxie.guardian.events.UntagPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class PlayerKickListener implements Listener {

    Guardian plugin;

    public PlayerKickListener(Guardian plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onKickEvent(PlayerKickEvent e){
        Player p = e.getPlayer();
        if(plugin.taggedPlayers.containsKey(p.getUniqueId()) && !e.getReason().toLowerCase().contains("spam")){
            UntagPlayerEvent event1 = new UntagPlayerEvent(p, UntagPlayerEvent.UntagCause.KICK);
            Bukkit.getServer().getPluginManager().callEvent(event1);
        }
    }
}
