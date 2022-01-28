package dev.trixxie.guardian.utils;

import org.bukkit.Bukkit;

public class ServerVersion {

    public static boolean oldVersion(){
        return Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.4") || Bukkit.getVersion().contains("1.3");
    }
}
