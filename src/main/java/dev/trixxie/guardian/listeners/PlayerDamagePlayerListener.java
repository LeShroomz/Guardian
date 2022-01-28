package dev.trixxie.guardian.listeners;

import dev.trixxie.guardian.Guardian;
import dev.trixxie.guardian.events.TagPlayerEvent;
import dev.trixxie.guardian.utils.Factions;
import dev.trixxie.guardian.utils.WorldGuard;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamagePlayerListener implements Listener {

    Guardian plugin;

    public PlayerDamagePlayerListener(Guardian plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void playerDamagePlayer(EntityDamageByEntityEvent e){
        Entity victim = e.getEntity();
        Entity damager = e.getDamager();
        if(damager instanceof Player && victim instanceof Player){
            if(((Player) damager).hasPermission("guardian.bypass") || ((Player) victim).hasPermission("guardian.bypass")) return;
            if(!((Player) victim).getGameMode().equals(GameMode.SURVIVAL)) return;
            if(plugin.usesSaberFactions){
                if(Factions.checkSafe((Player)victim) || Factions.checkSafe((Player)damager)) return;
            }
            if(plugin.usesWorldGuard){
                if(!WorldGuard.checkPVP((Player)victim) || !WorldGuard.checkPVP((Player)damager)) return;
            }

            TagPlayerEvent e1 = new TagPlayerEvent((Player) damager, (Player) victim, plugin.tagDuration);
            plugin.getServer().getPluginManager().callEvent(e1);
        }
        if(victim instanceof Player && damager instanceof Projectile && ((Projectile) damager).getShooter() instanceof Player){
            if(((Player) ((Projectile) damager).getShooter()).hasPermission("guardian.bypass") || ((Player) victim).hasPermission("guardian.bypass")) return;
            if(!((Player) victim).getGameMode().equals(GameMode.SURVIVAL)) return;
            if(plugin.usesSaberFactions){
                if(Factions.checkSafe((Player)victim) || Factions.checkSafe((Player) ((Projectile) damager).getShooter())) return;
            }
            if(plugin.usesWorldGuard){
                if(!WorldGuard.checkPVP((Player)victim) || !WorldGuard.checkPVP((Player) ((Projectile) damager).getShooter())) return;
            }

            if (damager instanceof Arrow) {
                TagPlayerEvent e1 = new TagPlayerEvent((Player) ((Projectile) damager).getShooter(), (Player) victim, plugin.tagDuration);
                plugin.getServer().getPluginManager().callEvent(e1);
            } else if (damager instanceof Snowball) {
                TagPlayerEvent e1 = new TagPlayerEvent((Player) ((Projectile) damager).getShooter(), (Player) victim, plugin.tagDuration);
                plugin.getServer().getPluginManager().callEvent(e1);
            } else if (damager instanceof Egg) {
                TagPlayerEvent e1 = new TagPlayerEvent((Player) ((Projectile) damager).getShooter(), (Player) victim, plugin.tagDuration);
                plugin.getServer().getPluginManager().callEvent(e1);
            } else if (damager instanceof ThrownPotion) {
                TagPlayerEvent e1 = new TagPlayerEvent((Player) ((Projectile) damager).getShooter(), (Player) victim, plugin.tagDuration);
                plugin.getServer().getPluginManager().callEvent(e1);
            } else if (damager instanceof EnderPearl) {
                return;
            }
        }
    }
}
