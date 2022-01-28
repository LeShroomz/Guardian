package dev.trixxie.guardian;

import com.tchristofferson.configupdater.ConfigUpdater;
import dev.trixxie.guardian.commands.GuardianCommand;
import dev.trixxie.guardian.commands.GuardianTabComplete;
import dev.trixxie.guardian.commands.TagCommand;
import dev.trixxie.guardian.events.UntagPlayerEvent;
import dev.trixxie.guardian.events.UntagPlayerEvent.UntagCause;
import dev.trixxie.guardian.listeners.*;
import dev.trixxie.guardian.placeholders.TagPlaceholder;
import dev.trixxie.guardian.utils.Metrics;
import dev.trixxie.guardian.utils.Updater;
import dev.trixxie.guardian.utils.Variables;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public final class Guardian extends JavaPlugin {

    public Logger log = Logger.getLogger("Minecraft");
    public Updater updater;
    public Variables vars;
    public boolean updateCheckEnabled = false;
    public boolean usesLibsDisguise = false;
    public boolean usesPAPI = false;
    public boolean usesSaberFactions = false;
    public boolean usesWorldGuard = false;
    public HashMap<UUID, Long> taggedPlayers = new HashMap<UUID, Long>();
    public ArrayList<UUID> killPlayers = new ArrayList<UUID>();
    public List<String> disableWorldNames = new ArrayList<String>();
    public int tagDuration = 10;
    public boolean combatlogBroadcast;
    public String combatlogBroadcastMsg;
    public boolean killLogger;
    public int combatlogs;
    public boolean tagMsgEnabled;
    public boolean removeFlight;
    public boolean removeInvisPotion;
    public boolean removeDisguise;
    public boolean removeCreative;
    public boolean preventEnderpearl;
    public boolean preventChorusfruit;
    public boolean taggedGlow;
    public boolean deathEffects;
    public String noFlyMsg;
    public String noInvisibility;

    @Override
    public void onEnable() {
        logInfo("Getting everything started!");
        checkDepends();
        loadConfig();
        initiateVariables();
        updateCheck();
        registerEvents();
        registerCommands();
        logHandler();
        tagTimer();

        Metrics metrics = new Metrics(this, 14099);
        metrics.addCustomChart(new Metrics.SingleLineChart("combatlogs", new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return combatlogs;
            }
        }));

    }

    @Override
    public void onDisable() {
        taggedPlayers.clear();
    }

    public void updateCheck(){
        if(updateCheckEnabled) {
            updater.updateCheck();
        }
    }

    public void loadConfig(){
        saveDefaultConfig();
        File configFile = new File(getDataFolder(), "config.yml");
        try {
            ConfigUpdater.update(this, "config.yml", configFile, Arrays.asList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        reloadConfig();
    }

    public void reloadConfigCommand(){
        reloadConfig();
        vars.getValues();
    }

    public void initiateVariables(){
        updater = new Updater(this);
        vars = new Variables(this);

        vars.getValues();
    }

    public void tagTimer(){
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Iterator<Map.Entry<UUID, Long>> iter = taggedPlayers.entrySet().iterator();
                while (iter.hasNext()){
                    Map.Entry<UUID, Long> c = iter.next();
                    Player player = getServer().getPlayer(c.getKey());
                    if(getCurrentTime() - (long) c.getValue().longValue() >= tagDuration){
                        iter.remove();
                        UntagPlayerEvent event = new UntagPlayerEvent(player, UntagCause.TIME_EXPIRE);
                        getServer().getPluginManager().callEvent(event);
                    }
                }
            }
        }, 0L, 20L);
    }

    public void logHandler(){
        log.addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                String s = record.getMessage();
                if(s.contains(" lost connection: ")){
                    String[] a = s.split(" ");
                    String DisconnectMsg = a[3];
                    PlayerQuitListener.setDisconnectMsg(DisconnectMsg);
                }
            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        });
    }

    public void checkDepends(){
        if(getServer().getPluginManager().getPlugin("FelineAPI") == null){
            getLogger().severe("FelineAPI not found, it is mandatory dependency, please download it and restart the server!\nhttps://www.spigotmc.org/resources/felineapi.99498/");
            getPluginLoader().disablePlugin(this);
            return;
        }
        if(getServer().getPluginManager().getPlugin("LibsDisguises") == null){
            usesLibsDisguise = false;
        } else {
            usesLibsDisguise = true;
            logInfo("LibsDisguises detected! Disguise removal will work now.");
        }
        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") == null){
            usesPAPI = false;
        } else {
            usesPAPI = true;
            new TagPlaceholder(this).register();
            logInfo("PlaceholderAPI detected! Guardian's placeholders are now usable.");
        }
        if(getServer().getPluginManager().getPlugin("Factions") == null){
            usesSaberFactions = false;
        } else {
            if(getServer().getPluginManager().getPlugin("Factions").getDescription().getAuthors().contains("Driftay")) {
                usesSaberFactions = true;
                logInfo("SaberFactions detected!");
            } else {
                usesSaberFactions = false;
                logWarning("Your Factions plugin is not supported!");
            }
        }
        if(getServer().getPluginManager().getPlugin("WorldGuard") == null){
            usesWorldGuard = false;
        } else {
            usesWorldGuard = true;
            logInfo("WorldGuard detected!");
        }
    }

    public void registerEvents(){
        getServer().getPluginManager().registerEvents(new PlayerDamagePlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerKickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerTagListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerUntagListener(this), this);
        getServer().getPluginManager().registerEvents(new FlightToggleListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerGamemodeListener(this), this);
        if(usesLibsDisguise){
            getServer().getPluginManager().registerEvents(new PlayerDisguiseListener(this), this);
        }
    }

    public void registerCommands(){
        getCommand("tag").setExecutor(new TagCommand(this));
        getCommand("guardian").setExecutor(new GuardianCommand(this));
        getCommand("guardian").setTabCompleter(new GuardianTabComplete());
    }

    public long getCurrentTime() {
        return System.currentTimeMillis() / 1000L;
    }

    public String tagTimeRemaining(String id) {
        return "" + (tagDuration - (getCurrentTime() - (Long) taggedPlayers.get(id).longValue()));
    }

    public long tagTime(String id) {
        return tagDuration - (getCurrentTime() - (Long) taggedPlayers.get(id).longValue());
    }

    public String ChatColor(String string) {
        return ChatColor.translateAlternateColorCodes('&', "&6<<Guardian>> " + string);
    }

    public void broadcast(String string) {
        getServer().broadcastMessage(ChatColor(string));
    }

    public void logInfo(String string) {
        getLogger().info(string);
    }

    public void logWarning(String string) {
        getLogger().warning(string);
    }
}
