package dev.trixxie.guardian.listeners;

import dev.trixxie.guardian.Guardian;
import dev.trixxie.guardian.events.UntagPlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerUntagListener implements Listener {

    Guardian plugin;

    public PlayerUntagListener (Guardian plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onUntag(UntagPlayerEvent e){
        Player p = e.getPlayer();
        if(plugin.taggedGlow){
            p.setGlowing(false);
        }
        p.sendMessage(plugin.ChatColor(plugin.getConfig().getString("messages.not_tagged_anymore"), true));
    }
}
