package tmlust.heavyrain.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Collection;

public class HeavyRainEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final boolean activated;

    public HeavyRainEvent(boolean activate, Collection<Player> playersOnline){
        if(activate)
            activated = true;
        else
            activated = false;
    }

    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
