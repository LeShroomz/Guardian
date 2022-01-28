package dev.trixxie.guardian.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TagPlayerEvent extends Event implements Cancellable {

    private Player damager;
    private Player victim;
    private int time;
    private boolean cancelled;

    public TagPlayerEvent(Player damager, Player victim, int time){
        this.damager = damager;
        this.victim = victim;
        this.time = time;
    }

    public Player getDamager() {
        return damager;
    }

    public Player getVictim() {
        return victim;
    }

    public int getTagTime(){
        return time;
    }

    private static HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
