package dev.trixxie.guardian.listeners;

import dev.trixxie.guardian.Guardian;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class PlayerGamemodeListener implements Listener {

    Guardian plugin;

    public PlayerGamemodeListener(Guardian plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e){
        Player p = e.getPlayer();
        if(!p.hasPermission("guardian.bypass") && plugin.removeCreative && plugin.taggedPlayers.containsKey(p.getUniqueId())){
            if(!p.getGameMode().equals(GameMode.SURVIVAL)){
                p.setGameMode(GameMode.SURVIVAL);
                e.setCancelled(true);
                p.sendMessage(plugin.ChatColor(plugin.getConfig().getString("messages.creative_removed")));
            }
        }
    }
}
