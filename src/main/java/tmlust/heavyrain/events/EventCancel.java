package tmlust.heavyrain.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import tmlust.heavyrain.HeavyRain;

public class EventCancel implements Listener {

    HeavyRain instance;

    public EventCancel(HeavyRain instance) {
        this.instance = instance;
    }

    @EventHandler
    public void PlayerBedEnterEvent(PlayerBedEnterEvent e) {
        if(instance.getCommands().isHeavyRainActivated()) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&cLos rayos te atormentan, no puedes dormir..."));
        }
    }
}
