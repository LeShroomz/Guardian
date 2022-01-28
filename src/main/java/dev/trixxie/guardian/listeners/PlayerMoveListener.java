package dev.trixxie.guardian.listeners;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import dev.trixxie.guardian.Guardian;
import dev.trixxie.guardian.events.UntagPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    Guardian plugin;

    public PlayerMoveListener(Guardian plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location l = p.getLocation();

        if(plugin.taggedPlayers.containsKey(p.getUniqueId())){
            if(plugin.usesSaberFactions){
                FLocation fLoc = new FLocation(l);
                if(Board.getInstance().getFactionAt(fLoc).isSafeZone()){
                    UntagPlayerEvent e1 = new UntagPlayerEvent(p, UntagPlayerEvent.UntagCause.SAFE_AREA);
                    Bukkit.getServer().getPluginManager().callEvent(e1);
                    return;
                }
            }
            if(plugin.usesWorldGuard){
                com.sk89q.worldedit.util.Location bukkitLoc = BukkitAdapter.adapt(l);
                LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(p);
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionQuery query = container.createQuery();
                ApplicableRegionSet set = query.getApplicableRegions(bukkitLoc);

                if(set.queryValue(localPlayer, Flags.PVP).equals(StateFlag.State.DENY)){
                    UntagPlayerEvent e1 = new UntagPlayerEvent(p, UntagPlayerEvent.UntagCause.SAFE_AREA);
                    Bukkit.getServer().getPluginManager().callEvent(e1);
                    return;
                }
            }
        }
    }
}
