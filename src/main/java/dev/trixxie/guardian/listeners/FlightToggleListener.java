package dev.trixxie.guardian.listeners;

import dev.trixxie.guardian.Guardian;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class FlightToggleListener implements Listener {

    Guardian plugin;

    public FlightToggleListener(Guardian plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onFlightToggle(PlayerToggleFlightEvent e){
        Player p = e.getPlayer();
        if(plugin.removeFlight && !p.hasPermission("guardian.bypass") && plugin.taggedPlayers.containsKey(p.getUniqueId()) && p.isFlying()){
            p.setFlying(false);
            e.setCancelled(true);
            p.sendMessage(plugin.ChatColor(plugin.noFlyMsg, true));
        }
    }
}
