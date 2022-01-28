package dev.trixxie.guardian.commands;

import dev.trixxie.guardian.Guardian;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TagCommand implements CommandExecutor {

    Guardian plugin;

    public TagCommand(Guardian plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(plugin.taggedPlayers.containsKey(p.getUniqueId())) {
                p.sendMessage(plugin.ChatColor(plugin.getConfig().getString("messages.tagged")));
            } else {
                p.sendMessage(plugin.ChatColor(plugin.getConfig().getString("messages.not_tagged")));
            }
        } else {
            plugin.logInfo("This command can't be run from console!");
        }
        return true;
    }
}
