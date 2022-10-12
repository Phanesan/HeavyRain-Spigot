package tmlust.heavyrain.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import tmlust.heavyrain.HeavyRain;

public class HeavyRainTask extends BukkitRunnable {
    HeavyRain plugin;
    int seconds;
    public HeavyRainTask(HeavyRain plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Bukkit.broadcastMessage(plugin.getConfigFile().getConfigFile().getString("message"));
    }
}
