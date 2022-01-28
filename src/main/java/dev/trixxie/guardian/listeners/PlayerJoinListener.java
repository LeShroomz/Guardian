package dev.trixxie.guardian.listeners;

import dev.trixxie.guardian.Guardian;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    Guardian plugin;

    public PlayerJoinListener(Guardian plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(plugin.updateCheckEnabled && p.hasPermission("guardian.update")){
            // UPDATE STUFF WIP
        }
    }
}
