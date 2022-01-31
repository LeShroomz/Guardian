package dev.trixxie.guardian.listeners;

import dev.trixxie.guardian.Guardian;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.events.DisguiseEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerDisguiseListener implements Listener {

    Guardian plugin;

    public PlayerDisguiseListener(Guardian plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDisguise(DisguiseEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(plugin.removeDisguise && plugin.taggedPlayers.containsKey(p.getUniqueId())){
                DisguiseAPI.undisguiseToAll(p);
                e.setCancelled(true);
                p.sendMessage(plugin.ChatColor(plugin.getConfig().getString("messages.disguise_removed"), true));
            }
        }
    }
}
