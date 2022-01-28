package dev.trixxie.guardian.listeners;

import dev.trixxie.guardian.Guardian;
import dev.trixxie.guardian.events.PlayerCombatlogEvent;
import dev.trixxie.guardian.events.UntagPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    Guardian plugin;

    public PlayerQuitListener(Guardian plugin){
        this.plugin = plugin;
    }

    public static String disconnectMsg = "";

    @EventHandler
    public void playerQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(p.hasPermission("guardian.bypass")) return;

        if(plugin.taggedPlayers.containsKey(p.getUniqueId())){
            if(!playerCombatLogged()){
                UntagPlayerEvent event1 = new UntagPlayerEvent(p, UntagPlayerEvent.UntagCause.LAGOUT);
                Bukkit.getServer().getPluginManager().callEvent(event1);
                return;
            }
        }
        if(plugin.combatlogBroadcast){
            plugin.broadcast(plugin.ChatColor(plugin.combatlogBroadcastMsg.replace("[PLAYER]", p.getName())));
        }
        if(plugin.killLogger){
            p.setHealth(0);
            plugin.killPlayers.add(p.getUniqueId());
        }
        PlayerCombatlogEvent event1 = new PlayerCombatlogEvent(plugin, p);
        plugin.getServer().getPluginManager().callEvent(event1);
        UntagPlayerEvent event2 = new UntagPlayerEvent(p, UntagPlayerEvent.UntagCause.COMBATLOG);
        plugin.getServer().getPluginManager().callEvent(event2);
        plugin.combatlogs++;

    }

    public static void setDisconnectMsg(String msg) {
        disconnectMsg = msg;
    }

    public boolean playerCombatLogged(){
        if (!disconnectMsg.equalsIgnoreCase("disconnect.overflow")
                && !disconnectMsg.equalsIgnoreCase("disconnect.genericreason")
                && !disconnectMsg.equalsIgnoreCase("disconnect.timeout")) {
            return true;
        }
        return false;
    }
}
