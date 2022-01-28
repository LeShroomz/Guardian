package dev.trixxie.guardian.utils;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Factions {

    public static boolean checkSafe(Player p){
        Location playerLocation = p.getLocation();
        FLocation fLoc = new FLocation(playerLocation);
        Faction fac = Board.getInstance().getFactionAt(fLoc);

        return fac.isSafeZone();
    }
}
