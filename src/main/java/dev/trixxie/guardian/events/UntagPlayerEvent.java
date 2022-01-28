package dev.trixxie.guardian.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UntagPlayerEvent extends Event implements Cancellable {

    public enum UntagCause {
        COMBATLOG,
        KICK,
        TIME_EXPIRE,
        DEATH,
        LAGOUT,
        SAFE_AREA
    }

    private Player player;
    private boolean cancelled;
    private final UntagCause cause;

    public UntagPlayerEvent(Player player, UntagCause uc){
        this.player = player;
        this.cause = uc;
    }

    public Player getPlayer(){
        return player;
    }

    public UntagCause getCause(){
        return cause;
    }

    public static HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public HandlerList getHandlers(){
        return handlerList;
    }

    @Override
    public boolean isCancelled(){
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b){
        this.cancelled = b;
    }


}
