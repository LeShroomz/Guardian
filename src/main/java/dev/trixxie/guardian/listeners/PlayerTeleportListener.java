package dev.trixxie.guardian.listeners;

import dev.trixxie.guardian.Guardian;
import dev.trixxie.guardian.utils.ServerVersion;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class PlayerTeleportListener implements Listener {

    Guardian plugin;

    public PlayerTeleportListener (Guardian plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerTeleport(PlayerTeleportEvent e){
        Player p = e.getPlayer();
        if(plugin.taggedPlayers.containsKey(p.getUniqueId())){
            if(e.getCause().equals(TeleportCause.COMMAND)
                    || e.getCause().equals(TeleportCause.PLUGIN)
                    || e.getCause().equals(TeleportCause.END_GATEWAY)
                    || e.getCause().equals(TeleportCause.END_PORTAL)
                    || e.getCause().equals(TeleportCause.UNKNOWN)){
                e.setCancelled(true);
                p.sendMessage(plugin.ChatColor(plugin.getConfig().getString("messages.teleportation_prevented")));
            }
            if(plugin.preventChorusfruit && !ServerVersion.oldVersion()){
                if(e.getCause().equals(TeleportCause.CHORUS_FRUIT)){
                    e.setCancelled(true);
                    p.sendMessage(plugin.ChatColor(plugin.getConfig().getString("messages.teleportation_prevented")));
                }
            }
            if(plugin.preventEnderpearl){
                if(e.getCause().equals(TeleportCause.ENDER_PEARL)){
                    e.setCancelled(true);
                    p.sendMessage(plugin.ChatColor(plugin.getConfig().getString("messages.teleportation_prevented")));
                }
            }
        }
    }
}
