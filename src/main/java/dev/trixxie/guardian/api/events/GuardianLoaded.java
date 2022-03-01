package dev.trixxie.guardian.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when Guardian has loaded
 */
public class GuardianLoaded extends Event {

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
