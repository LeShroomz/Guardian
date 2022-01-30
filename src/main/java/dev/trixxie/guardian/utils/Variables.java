package dev.trixxie.guardian.utils;

import dev.trixxie.guardian.Guardian;

public class Variables {

    Guardian plugin;

    public Variables(Guardian plugin){
        this.plugin = plugin;
    }

    public void getValues(){
        plugin.getLogger().info("Loading config.yml");
        plugin.updateCheckEnabled = plugin.getConfig().getBoolean("update-checker");
        plugin.combatlogBroadcast = plugin.getConfig().getBoolean("messages.broadcast_combatlog.enabled");
        plugin.combatlogBroadcastMsg = plugin.getConfig().getString("messages.broadcast_combatlog.msg");
        plugin.killLogger = plugin.getConfig().getBoolean("killCombatlogger");
        plugin.disableWorldNames = plugin.getConfig().getStringList("disabled_worlds");
        plugin.tagMsgEnabled = plugin.getConfig().getBoolean("messages.tag_message.enabled");
        plugin.removeFlight =  plugin.getConfig().getBoolean("removeFly");
        plugin.removeInvisPotion = plugin.getConfig().getBoolean("removeInvisibilityPotionEffect");
        plugin.noInvisibility = plugin.getConfig().getString("messages.inivisibility_potion_removed");
        plugin.removeDisguise = plugin.getConfig().getBoolean("removeDisguise");
        plugin.removeCreative = plugin.getConfig().getBoolean("removeCreative");
        plugin.preventEnderpearl = plugin.getConfig().getBoolean("preventEnderpearl");
        plugin.preventChorusfruit = plugin.getConfig().getBoolean("preventChorusfruit");
        plugin.taggedGlow = plugin.getConfig().getBoolean("taggedGlow");
        plugin.deathEffects = plugin.getConfig().getBoolean("extras.death_effects.enabled");
        plugin.noFlyMsg = plugin.getConfig().getString("messages.flying_removed");
    }
}
