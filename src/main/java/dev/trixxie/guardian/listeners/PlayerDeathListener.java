package dev.trixxie.guardian.listeners;

import dev.trixxie.guardian.Guardian;
import dev.trixxie.guardian.events.UntagPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World.Spigot;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    public Guardian plugin;

    public PlayerDeathListener(Guardian plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player p = e.getEntity().getPlayer();
        if(plugin.taggedPlayers.containsKey(p.getUniqueId())){
            UntagPlayerEvent event1 = new UntagPlayerEvent(p, UntagPlayerEvent.UntagCause.DEATH);
            Bukkit.getServer().getPluginManager().callEvent(event1);

            // Death effect thingy ZAP BOOM
            if(plugin.deathEffects){
                boolean silent = plugin.getConfig().getBoolean("extras.death_effects.silent");

                Location l = p.getLocation();
                World w = p.getWorld();
                Spigot spigot = w.spigot();

                spigot.strikeLightningEffect(l, silent);
            }
        }
        plugin.killPlayers.remove(p.getUniqueId());
    }
}
