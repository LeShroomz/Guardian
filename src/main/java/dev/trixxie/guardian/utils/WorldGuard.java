package dev.trixxie.guardian.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import dev.trixxie.guardian.events.UntagPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WorldGuard {

    public static boolean checkPVP(Player p){
        com.sk89q.worldedit.util.Location bukkitLoc = BukkitAdapter.adapt(p.getLocation());
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(p);
        RegionContainer container = com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(bukkitLoc);

        if(set.queryValue(localPlayer, Flags.PVP).equals(StateFlag.State.ALLOW)){
            UntagPlayerEvent e1 = new UntagPlayerEvent(p, UntagPlayerEvent.UntagCause.SAFE_AREA);
            Bukkit.getServer().getPluginManager().callEvent(e1);
            return true;
        } else {
            return false;
        }
    }
}
