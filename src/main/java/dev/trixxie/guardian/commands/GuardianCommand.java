package dev.trixxie.guardian.commands;

import dev.trixxie.guardian.Guardian;
import dev.trixxie.guardian.utils.VersionUtility;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class GuardianCommand implements CommandExecutor {

    Guardian plugin;

    public GuardianCommand(Guardian plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("reload")){
                if(sender instanceof Player) {
                    if(((Player)sender).hasPermission("guardian.reload")) {
                        plugin.reloadConfigCommand();
                        sender.sendMessage(plugin.ChatColor("&aConfig reloaded!", true));
                    } else {
                        sender.sendMessage(plugin.ChatColor("&cYou do not have permission to do this (guardian.reload)", true));
                    }
                } else {
                    plugin.logInfo("Config reloaded!");
                }
            }
            if(args[0].equalsIgnoreCase("version")){
                if(sender instanceof Player){
                    if(((Player)sender).hasPermission("guardian.version")){
                        sender.sendMessage(plugin.ChatColor("&cThis command can be run from Console only!", true));
                    } else {
                        sender.sendMessage(plugin.ChatColor("&cYou do not have permission to do this (guardian.version)", true));
                    }
                } else {
                    List<String> versionList = new ArrayList<>();

                    addJavaVersionInformation(versionList);
                    versionList.add("&f");
                    addServerVersionInformation(versionList);
                    versionList.add("&f");
                    addDependencyInformation(versionList);
                    versionList.add("&f");
                    addPluginVersionInformation(versionList);
                    versionList.add("&f");

                    List<String> colorList = dev.trixxie.felineapi.utility.MessageUtility.colorList(versionList);
                    for(String message : colorList) {
                        sender.sendMessage(message);
                    }

                }
            }
        }
        return true;
    }

    private void addJavaVersionInformation(List<String> messageList) {
        try {
            String javaVersion = System.getProperty("java.version");
            String javaVendor = System.getProperty("java.vendor");
            messageList.add("&f&lJava Version: &7" + javaVersion);
            messageList.add("&f&lJava Vendor: &7" + javaVendor);
        } catch(SecurityException | IllegalArgumentException | NullPointerException ex) {
            messageList.add("&f&lJava Version: &7Unknown");
        }
    }

    private void addServerVersionInformation(List<String> messageList) {
        String version = Bukkit.getVersion();
        String bukkitVersion = Bukkit.getBukkitVersion();
        String minecraftVersion = VersionUtility.getMinecraftVersion();
        String nmsVersion = VersionUtility.getNetMinecraftServerVersion();

        messageList.add("&f&lServer Version: &7" + version);
        messageList.add("&f&lBukkit Version: &7" + bukkitVersion);
        messageList.add("&f&lMinecraft Version: &7" + minecraftVersion);
        messageList.add("&f&lNMS Version: &7" + nmsVersion);
    }

    private void addDependencyInformation(List<String> messageList) {
        PluginDescriptionFile information = plugin.getDescription();
        messageList.add("&f&lDependency Information:");

        List<String> loadBeforeList = information.getLoadBefore();
        List<String> dependList = information.getDepend();
        List<String> softDependList = information.getSoftDepend();

        List<String> fullDependencyList = new ArrayList<>(loadBeforeList);
        fullDependencyList.addAll(dependList);
        fullDependencyList.addAll(softDependList);

        PluginManager pluginManager = Bukkit.getPluginManager();
        for(String dependencyName : fullDependencyList) {
            Plugin dependency = pluginManager.getPlugin(dependencyName);
            if(dependency == null) {
                continue;
            }

            PluginDescriptionFile dependencyInformation = dependency.getDescription();
            String dependencyFullName = dependencyInformation.getFullName();
            messageList.add("&f&l - &7" + dependencyFullName);
        }
    }

    private void addPluginVersionInformation(List<String> messageList) {
        String pluginVersion = getPluginVersion();

        messageList.add("&f&lGuardian by Trixxie");
        messageList.add("&f&lPlugin Version: &7" + pluginVersion);
    }

    private String getPluginVersion() {
        PluginDescriptionFile information = plugin.getDescription();
        return information.getVersion();
    }
}
