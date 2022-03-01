package dev.trixxie.guardian.events;

import dev.trixxie.guardian.Guardian;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when player combat logs
 */
public class PlayerCombatlogEvent extends Event implements Cancellable {

    private boolean cancelled;
    private Player player;
    Guardian plugin;

    private static final HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public PlayerCombatlogEvent(Guardian plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public long getTagTimeRemaining() {
        return (plugin.tagDuration - (plugin.getCurrentTime() - (Long) plugin.taggedPlayers.get(player.getName()).longValue()));
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
