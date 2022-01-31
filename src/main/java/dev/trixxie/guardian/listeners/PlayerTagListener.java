package dev.trixxie.guardian.listeners;

import dev.trixxie.guardian.Guardian;
import dev.trixxie.guardian.events.TagPlayerEvent;
import dev.trixxie.guardian.utils.ServerVersion;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerTagListener implements Listener {

    Guardian plugin;

    public PlayerTagListener(Guardian plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onTagEvent(TagPlayerEvent e){
        Player damager = e.getDamager();
        Player victim = e.getVictim();
        tagDamager(damager, victim);
        tagVictim(victim, damager);
    }

    private void tagDamager(Entity damager, Entity victim){
        if(damager instanceof Player){
            Player p = (Player) damager;
            if(!plugin.disableWorldNames.contains(p.getWorld().getName())){
                Location l = p.getLocation();
                if(!plugin.taggedPlayers.containsKey(p.getUniqueId())){
                    plugin.taggedPlayers.put(p.getUniqueId(), plugin.getCurrentTime());

                    if(plugin.tagMsgEnabled){
                        p.sendMessage(plugin.ChatColor(plugin.getConfig().getString("messages.tagged"), true));
                    }
                    if(plugin.removeFlight && p.isFlying()){
                        p.setFlying(false);
                        p.sendMessage(plugin.ChatColor(plugin.noFlyMsg, true));
                    }
                    if(plugin.removeInvisPotion){
                        removeInvis(p);
                    }
                    if(plugin.usesLibsDisguise && plugin.removeDisguise){
                        if(DisguiseAPI.isDisguised(p)){
                            DisguiseAPI.undisguiseToAll(p);
                            p.sendMessage(plugin.ChatColor(plugin.getConfig().getString("disguise_removed"), true));
                        }
                    }

                    if(plugin.taggedGlow && !ServerVersion.oldVersion()){
                        p.setGlowing(true);
                    } else if(plugin.taggedGlow && ServerVersion.oldVersion()){
                        plugin.logWarning("[Guardian] You have enabled Glowing effect on tagged players but you are running too old server version (" + Bukkit.getServer().getVersion() + ")");
                    }
                } else {
                    plugin.taggedPlayers.remove(p.getUniqueId());
                    plugin.taggedPlayers.put(p.getUniqueId(), plugin.getCurrentTime());
                    if(plugin.usesLibsDisguise && plugin.removeDisguise){
                        if(DisguiseAPI.isDisguised(p)){
                            DisguiseAPI.undisguiseToAll(p);
                            p.sendMessage(plugin.ChatColor(plugin.getConfig().getString("disguise_removed"), true));
                        }
                    }
                    if(plugin.removeFlight && p.isFlying()){
                        p.setFlying(false);
                        p.sendMessage(plugin.ChatColor(plugin.noFlyMsg, true));
                    }

                    if(plugin.removeInvisPotion){
                        removeInvis(p);
                    }

                    if(plugin.removeCreative && p.getGameMode().equals(GameMode.CREATIVE)){
                        p.setGameMode(GameMode.SURVIVAL);
                    }

                    if(plugin.taggedGlow && !ServerVersion.oldVersion()){
                        p.setGlowing(true);
                    } else if(plugin.taggedGlow && ServerVersion.oldVersion()){
                        plugin.logWarning("[Guardian] You have enabled Glowing effect on tagged players but you are running too old server version (" + Bukkit.getServer().getVersion() + ")");
                    }
                }
            }
        }
    }

    private void tagVictim(Entity victim, Entity damager){
        if(victim instanceof Player){
            Player p = (Player) victim;
            Location l = p.getLocation();
            if(!plugin.disableWorldNames.contains(p.getWorld().getName())){
                if(!plugin.taggedPlayers.containsKey(p.getUniqueId())){
                    plugin.taggedPlayers.put(p.getUniqueId(), plugin.getCurrentTime());

                    if(plugin.tagMsgEnabled){
                        p.sendMessage(plugin.ChatColor(plugin.getConfig().getString("messages.tag_message.msg"), false));
                    }
                    if(plugin.removeFlight && p.isFlying()){
                        p.setFlying(false);
                        p.sendMessage(plugin.ChatColor(plugin.noFlyMsg, true));
                    }
                    if(plugin.removeInvisPotion){
                        removeInvis(p);
                    }
                    if(plugin.usesLibsDisguise && plugin.removeDisguise){
                        if(DisguiseAPI.isDisguised(p)){
                            DisguiseAPI.undisguiseToAll(p);
                            p.sendMessage(plugin.ChatColor(plugin.getConfig().getString("disguise_removed"), true));
                        }
                    }
                    if(plugin.taggedGlow && !ServerVersion.oldVersion()){
                        p.setGlowing(true);
                    } else if(plugin.taggedGlow && ServerVersion.oldVersion()){
                        plugin.logWarning("[Guardian] You have enabled Glowing effect on tagged players but you are running too old server version (" + Bukkit.getServer().getVersion() + ")");
                    }
                } else {
                    plugin.taggedPlayers.remove(p.getUniqueId());
                    plugin.taggedPlayers.put(p.getUniqueId(), plugin.getCurrentTime());
                    if(plugin.usesLibsDisguise && plugin.removeDisguise){
                        if(DisguiseAPI.isDisguised(p)){
                            DisguiseAPI.undisguiseToAll(p);
                            p.sendMessage(plugin.ChatColor(plugin.getConfig().getString("disguise_removed"), true));
                        }
                    }
                    if(plugin.removeFlight && p.isFlying()){
                        p.setFlying(false);
                        p.sendMessage(plugin.ChatColor(plugin.noFlyMsg, true));
                    }

                    if(plugin.removeInvisPotion){
                        removeInvis(p);
                    }

                    if(plugin.removeCreative && p.getGameMode().equals(GameMode.CREATIVE)){
                        p.setGameMode(GameMode.SURVIVAL);
                    }

                    if(plugin.taggedGlow && !ServerVersion.oldVersion()){
                        p.setGlowing(true);
                    } else if(plugin.taggedGlow && ServerVersion.oldVersion()){
                        plugin.logWarning("[Guardian] You have enabled Glowing effect on tagged players but you are running too old server version (" + Bukkit.getServer().getVersion() + ")");
                    }
                }
            }
        }
    }

    private void removeInvis(Player pl){
        for(PotionEffect potion : pl.getActivePotionEffects()){
            if(potion.getType().equals(PotionEffectType.INVISIBILITY)){
                pl.removePotionEffect(PotionEffectType.INVISIBILITY);
                pl.sendMessage(plugin.ChatColor(plugin.noInvisibility, true));
            }
        }
    }
}
